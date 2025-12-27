package com.kaii.dentix.domain.huawei2.dto;

import lombok.Data;

/**
 * Huawei SaaS queryInstance 응답 구조 - 단일 인스턴스 정보
 *
 * info: [
 *   {
 *     "instanceId": "...",
 *     "appInfo": { ... },
 *     "usageInfo": [ ... ]
 *   }
 * ]
 */
@Data
public class CheckInstanceInfo {

    /** 인스턴스 ID */
    private String instanceId;

    /** 애플리케이션 접속 정보 */
    private AppInfo appInfo;

    /** 사용량 정보 (없을 수도 있음) */
    private UsageInfo[] usageInfo;  // Huawei 문서 배열 구조

    /** 암호화 여부 (문서 예시에는 encryptType 같이 내려올 수 있음) */
    private String encryptType;

    public CheckInstanceInfo() {}

    public CheckInstanceInfo(String instanceId, AppInfo appInfo) {
        this.instanceId = instanceId;
        this.appInfo = appInfo;
    }
}