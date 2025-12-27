package com.kaii.dentix.domain.huawei2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshInstanceResponse {
    private String resultCode;   // "000000"
    private String resultMsg;    // "success"
}