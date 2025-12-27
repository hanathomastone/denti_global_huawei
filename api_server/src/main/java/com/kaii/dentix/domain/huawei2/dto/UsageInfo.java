package com.kaii.dentix.domain.huawei2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsageInfo {

    private String relatedInstanceId;
    private Double usageValue;
    private String statisticalTime;
    private String dashboardUrl;
}