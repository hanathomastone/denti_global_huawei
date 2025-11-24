package com.kaii.dentix.domain.huawei.dto;

import lombok.Data;

@Data
public class CallbackRequest {
    private String eventType; // create / renew / cancel 등
    private String orderId;
    private String instanceId;
}