package com.kaii.dentix.domain.huawei.dto;

import lombok.Data;

/**
 * Huawei SaaS - InstanceInfo (createInstance에서 반환하는 단일 인스턴스 정보)
 * template.zip 공식 샘플 기반 재구성
 */
@Data
public class InstanceInfo {

    /** 인스턴스 ID */
    private String instanceId;

    /** 앱 접속 정보 */
    private AppInfo appInfo;

    /** 사용 정보 배열 (없어도 됨) */
    private UsageInfo[] usageInfo;

    /** 암호화 여부 (필수 아님) */
    private String encryptType;

    public InstanceInfo() {}

    public InstanceInfo(String instanceId, AppInfo appInfo) {
        this.instanceId = instanceId;
        this.appInfo = appInfo;
    }
}