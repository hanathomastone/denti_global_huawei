package com.kaii.dentix.domain.huawei2.application;

import com.amazonaws.util.EC2MetadataUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaii.dentix.domain.huawei2.application.IsvProduceService;
import com.kaii.dentix.domain.huawei2.dto.AppInfo;
import com.kaii.dentix.domain.huawei2.dto.CheckInstanceInfo;
import com.kaii.dentix.domain.huawei2.util.ChangeStatus;
import com.kaii.dentix.domain.huawei2.util.ResultCodeEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class IsvProduceServiceImpl implements IsvProduceService {

    @Override
    public Object newInstance(Map<String, Object> params) {

        String businessId = (String) params.get("businessId");
        String instanceId = businessId; // Huawei 권장 방식

        // ⬇️ 여기서 비즈니스 로직 (비동기가 권장됨)
        // organizationService.create(instanceId, params);

        return ResultCodeEnum.SUCCESS.toResponse(instanceId);
    }


    @Override
    public Object queryInstance(Map<String, Object> params) {

        String instanceId = (String) params.get("instanceId");

        // 여러 개의 instanceId 요청 시 쉼표로 분리
        String[] ids = instanceId.split(",");

        List<CheckInstanceInfo> list = new ArrayList<>();

        for (String id : ids) {

            // Huawei가 요구하는 appInfo 구조
            AppInfo app = new AppInfo();
            app.setFrontEndUrl("https://denti-cn.thomabio.com");
            app.setAdminUrl("https://denti-cn.thomabio.com/admin");
            app.setUserName("admin6");
            app.setPassword("test1234!"); // 필요 시 동적 생성 가능
            app.setMemo("Welcome to DentiGlobal!");

            // 공식 응답 객체
            CheckInstanceInfo check = new CheckInstanceInfo();
            check.setInstanceId(id);
            check.setAppInfo(app);

            list.add(check);
        }

        return ResultCodeEnum.SUCCESS.toResponse(list);
    }

    @Override
    public Object refreshInstance(Map<String, Object> params) {

        String instanceId = (String) params.get("instanceId");
        String expireTime = (String) params.get("expireTime");
        String scene = (String) params.get("scene");
        String productId = (String) params.get("productId");

        if (instanceId == null || expireTime == null || scene == null) {
            return ResultCodeEnum.PARAM_ERROR.toResponse("missing required parameters");
        }

        LocalDateTime newExpireDate;
        try {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            newExpireDate = LocalDateTime.parse(expireTime, fmt);
        } catch (Exception e) {
            return ResultCodeEnum.PARAM_ERROR.toResponse("invalid expireTime format");
        }

        switch (scene) {

            case "TRIAL_TO_FORMAL":
                break;

            case "RENEWAL":
                break;

            case "CANCEL_RENEW":
                break;

            case "RENEWAL_CHANGE":
                // ⬅ 갱신 변경은 이 분기
                // org.changePlan(productId, newExpireDate);
                break;

            default:
                return ResultCodeEnum.PARAM_ERROR.toResponse("invalid scene value");
        }

        return ResultCodeEnum.SUCCESS.toResponse(null);
    }

    @Override
    public Object updateInstanceStatus(Map<String, Object> params) {

        String instanceId = (String) params.get("instanceId");
        String status = (String) params.get("status");

        if (ChangeStatus.FREEZE.equals(status)) {
            // FREEZE 처리
            // organizationService.freeze(instanceId);
        } else if (ChangeStatus.UNFREEZE.equals(status)) {
            // UNFREEZE 처리
            // organizationService.unfreeze(instanceId);
        } else {
            return ResultCodeEnum.PARAM_ERROR.toResponse("Invalid status");
        }

        return ResultCodeEnum.SUCCESS.toResponse(null);
    }


    @Override
    public Object releaseInstance(Map<String, Object> params) {

        String instanceId = (String) params.get("instanceId");

        // 인스턴스 해제 처리
        // organizationService.disable(instanceId);

        return ResultCodeEnum.SUCCESS.toResponse(null);
    }


    @Override
    public Object upgradeInstance(Map<String, Object> params) {

        String instanceId = (String) params.get("instanceId");
        String productId = (String) params.get("productId");

        // 플랜 업그레이드 처리
        // subscriptionService.upgradePlan(instanceId, productId);

        return ResultCodeEnum.SUCCESS.toResponse(null);
    }
    @Override
    public Object changeInstanceCheck(Map<String, Object> params) {

        Object rawProductInfo = params.get("productInfo");

        Map<String, Object> productInfo;

        // case 1: JSON 객체로 온 경우
        if (rawProductInfo instanceof Map) {
            productInfo = (Map<String, Object>) rawProductInfo;
        }
        // case 2: JSON 문자열로 온 경우 → Jackson으로 파싱
        else if (rawProductInfo instanceof String) {
            try {
                productInfo = new ObjectMapper().readValue((String) rawProductInfo, Map.class);
            } catch (JsonProcessingException e) {
                return ResultCodeEnum.PARAM_ERROR.toResponse("Invalid productInfo JSON");
            }
        }
        // 그 외는 오류
        else {
            return ResultCodeEnum.PARAM_ERROR.toResponse("productInfo format invalid");
        }

        String instanceId = (String) params.get("instanceId");
        String productId  = (String) productInfo.get("productId");

        // ... 검증 로직 ...
        return ResultCodeEnum.SUCCESS.toResponse(null);
    }
}