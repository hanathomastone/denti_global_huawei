package com.kaii.dentix.domain.jwt;

import com.kaii.dentix.global.common.error.ErrorResponse;
import com.kaii.dentix.global.common.error.exception.TokenExpiredException;
import com.kaii.dentix.global.common.response.ResponseMessage;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

import static com.kaii.dentix.global.config.WebSecurityConfig.EXCLUDE_URLS;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {


        String requestURI = request.getRequestURI();

        // ⭐ 1. ISV API 완전 제외 (가장 중요)
        if (requestURI.startsWith("/isv")
                || requestURI.startsWith("/api/isv")) {
            chain.doFilter(request, response);
            return;
        }

        // ⭐ 2. context-path 제거
        if (requestURI.startsWith("/api/")) {
            requestURI = requestURI.substring(4);
        } else if (requestURI.equals("/api")) {
            requestURI = "/";
        }

        final String uri = requestURI;

        // ⭐ 3. permitAll 검사
        boolean permitAll = Arrays.stream(EXCLUDE_URLS)
                .anyMatch(url -> uri.startsWith(url.replace("*", "")));

        if (permitAll) {
            chain.doFilter(request, response);
            return;
        }

        try {
            // 🍀 4) Authorization 헤더에서 토큰 추출
            String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);

            if (StringUtils.isBlank(accessToken)) {
                throw new TokenExpiredException();
            }

            if (accessToken.startsWith("Bearer ")) {
                accessToken = accessToken.substring(7);
            }

            // 🍀 5) 만료/비인가 토큰 검사
            if (jwtTokenUtil.isExpired(accessToken, TokenType.AccessToken)) {
                throw new TokenExpiredException();
            }

            if (jwtTokenUtil.isUnauthorized(accessToken, TokenType.AccessToken)) {
                throw new TokenExpiredException();
            }

            // 🍀 6) 인증객체 생성 → SecurityContext 등록
            Authentication authentication =
                    jwtTokenUtil.getAuthentication(accessToken, TokenType.AccessToken);

            if (authentication != null) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info("✅ SecurityContext 설정 완료: {}", authentication.getAuthorities());
            }

        } catch (Exception e) {
            log.warn("❌ JWT 인증 실패: {}", e.getMessage());
            ErrorResponse.of(response, HttpStatus.FORBIDDEN, ResponseMessage.FORBIDDEN_MSG);
            return;
        }

        // 🍀 7) 다음 필터로
        chain.doFilter(request, response);
    }
}
