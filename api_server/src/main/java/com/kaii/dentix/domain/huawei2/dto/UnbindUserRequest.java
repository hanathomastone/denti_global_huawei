package com.kaii.dentix.domain.huawei2.dto;

import lombok.Data;

@Data
public class UnbindUserRequest {
    private String instanceId;
    private String userId;
}