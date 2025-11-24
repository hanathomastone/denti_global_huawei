package com.kaii.dentix.domain.huawei.dto;

import lombok.Data;

@Data
public class UnbindUserRequest {
    private String instanceId;
    private String userId;
}