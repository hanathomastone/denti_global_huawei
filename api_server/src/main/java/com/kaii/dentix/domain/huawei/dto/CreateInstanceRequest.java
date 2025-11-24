package com.kaii.dentix.domain.huawei.dto;

import lombok.Data;
@Data
public class CreateInstanceRequest {

    private String activity;      // "newInstance"
    private String businessId;    // Huawei 전역 고유 ID
    private String orderId;       // 주문 ID
    private String orderLineId;   // 주문 Line ID
    private String testFlag;      // optional ("1" for debug)
}