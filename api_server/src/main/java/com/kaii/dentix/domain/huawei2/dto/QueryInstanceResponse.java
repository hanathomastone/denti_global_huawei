package com.kaii.dentix.domain.huawei2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryInstanceResponse {
    private String resultCode;
    private String resultMsg;
    private String encryptType;
    private List<HuaweiInstanceInfo> info;
}