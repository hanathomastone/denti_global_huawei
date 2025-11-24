package com.kaii.dentix.domain.huawei.dto;

import lombok.Data;

@Data
public class BindUserRequest {
    private String instanceId;
    private String userId;       // Huawei 사용자 ID
    private String userAccount;  // 이메일 또는 계정명
}