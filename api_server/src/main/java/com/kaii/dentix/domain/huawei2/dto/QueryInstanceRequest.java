package com.kaii.dentix.domain.huawei2.dto;

import lombok.Data;

@Data
public class QueryInstanceRequest {

    private String activity;      // "queryInstance"
    private String instanceId;    // "id1,id2,id3"
    private String testFlag;      // optional
}