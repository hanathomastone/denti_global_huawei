package com.kaii.dentix.domain.huawei.dto;

import lombok.Data;

@Data
public class UpdateInstanceStatusRequest {

    private String activity;     // "updateInstanceStatus"
    private String instanceId;
    private String status;       // FREEZE / UNFREEZE
    private String testFlag;     // optional
}