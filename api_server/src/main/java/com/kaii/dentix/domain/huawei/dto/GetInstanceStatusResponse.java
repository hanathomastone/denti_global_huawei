package com.kaii.dentix.domain.huawei.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetInstanceStatusResponse {
    private String instanceId;
    private String status; // active / inactive / error
}