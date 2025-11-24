package com.kaii.dentix.domain.huawei.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HuaweiInstanceInfo {

    private String instanceId;
    private AppInfo appInfo;
    private List<UsageInfo> usageInfo;
}