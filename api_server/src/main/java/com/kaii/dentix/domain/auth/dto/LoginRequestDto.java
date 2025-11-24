package com.kaii.dentix.domain.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class LoginRequestDto {
    @NotBlank
    private String userType; // "admin" 또는 "user"
    @Pattern(regexp = "^[a-zA-Z0-9_.@-]{3,50}$")
    @NotBlank
    private String loginId;
    @Size(min = 6, max = 100)
    @NotBlank
    private String password;

    // USER 로그인 시만 아래 값 필요, ADMIN이면 없어도 됨
//    private String userDeviceModel;
//    private String userDeviceManufacturer;
//    private String userOsVersion;
//    private String userDeviceTokend;

}
