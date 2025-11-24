package com.kaii.dentix.domain.huawei.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateInstanceResponse {
    private String resultCode;   // "000000" or "000004"
    private String resultMsg;    // "success"
    private String instanceId;   // businessId 사용 권장
}