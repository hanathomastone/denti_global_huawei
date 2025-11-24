package com.kaii.dentix.domain.huawei.dto;

import lombok.Data;

@Data
public class RefreshInstanceRequest {

    private String activity;     // "refreshInstance"
    private String scene;        // RENEWAL, TRIAL_TO_FORMAL, ...
    private String orderId;
    private String orderLineId;
    private String instanceId;
    private String productId;    // optional
    private String expireTime;   // yyyyMMddHHmmss
    private String testFlag;     // optional
}