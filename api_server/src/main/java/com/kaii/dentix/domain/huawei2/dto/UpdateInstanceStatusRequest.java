package com.kaii.dentix.domain.huawei2.dto;

import lombok.Data;

@Data
public class UpdateInstanceStatusRequest {

    private String activity;     // "updateInstanceStatus"
    private String instanceId;
    private String status;       // FREEZE / UNFREEZE
    private String testFlag;     // optional
}