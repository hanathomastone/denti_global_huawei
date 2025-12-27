package com.kaii.dentix.global.config;

import com.kaii.dentix.domain.jwt.JwtAuthenticationFilter;
import com.kaii.dentix.domain.jwt.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtTokenUtil jwtTokenUtil;

    public static final String[] EXCLUDE_URLS = {
            "/actuator/health",
            "/docs/*",

            "/login", "/login/**",
            "/password/**",

            "/service-agreement",
            "/contents/**",
            "/isv/**",
            "/organizations/check/**",

            "/admin/login",
            "/admin/register/**",
            "/admin/account/**",
            "/admin/password",
            "/admin/find-password",
            "/admin/auto-login"
    };
    @Bean public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {

        http
                .httpBasic(AbstractHttpConfigurer::disable)

                // ✅ sCSRF 재활성화 (중요)
                .csrf(csrf -> csrf
                        .csrfTokenRepository(
                                CookieCsrfTokenRepository.withHttpOnlyFalse()
                        )
                        .ignoringRequestMatchers(
                                "/login/**",
                                "/admin/login",
                                "/admin/auto-login",
                                "/isv/**"          // ⭐ 이거 반드시 필요
                        )
                )

                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // ✅ 헤더는 최소한만 (nginx와 중복 제거)
                .headers(headers -> headers
                        .xssProtection(xss -> xss.disable()) // 헤더 직접 추가 안 함
                        .contentTypeOptions(content -> {})
                        .frameOptions(frame -> frame.deny())
                )

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(EXCLUDE_URLS).permitAll()

                        // 파일 다운로드
                        .requestMatchers(
                                "/admin/billing/export/excel",
                                "/admin/user/bulk-upload/template"
                        ).permitAll()

                        // Admin
                        .requestMatchers("/admin/**")
                        .hasAnyRole("ADMIN", "SUPER_ADMIN")

                        // SuperAdmin
                        .requestMatchers("/superadmin/**")
                        .hasAnyRole("SUPER_ADMIN")

                        .anyRequest().authenticated()
                )

                .addFilterBefore(
                        new JwtAuthenticationFilter(jwtTokenUtil),
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOriginPatterns(List.of(
                "http://localhost:5173",
                "https://denti-cn.thomabio.com"
        ));
        config.setAllowedMethods(List.of(
                "GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"
        ));
        config.setAllowedHeaders(List.of("*"));
        config.setExposedHeaders(List.of("X-CSRF-TOKEN"));
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
