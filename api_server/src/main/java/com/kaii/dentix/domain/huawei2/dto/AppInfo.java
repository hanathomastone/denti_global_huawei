package com.kaii.dentix.domain.huawei2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppInfo {

    private String frontEndUrl;
    private String adminUrl;
    private String userName;
    private String password;
    private String memo;
}