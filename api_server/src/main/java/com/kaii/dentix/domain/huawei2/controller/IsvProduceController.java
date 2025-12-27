package com.kaii.dentix.domain.huawei2.controller;
import com.kaii.dentix.domain.huawei2.application.IsvProduceService;
import com.kaii.dentix.domain.huawei2.util.Activity;
import com.kaii.dentix.domain.huawei2.util.ResultCodeEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/isv")
@RequiredArgsConstructor
public class IsvProduceController {

    private final IsvProduceService produceService;

    @PostMapping("/produce")
    public Object produce(@RequestBody Map<String, Object> params) {

        String activity = (String) params.get("activity");

        switch (activity) {

            case Activity.NEW_INSTANCE:
                return produceService.newInstance(params);

            case Activity.QUERY_INSTANCE:
                return produceService.queryInstance(params);

            case Activity.REFRESH_INSTANCE:
                return produceService.refreshInstance(params);

            case Activity.UPDATE_INSTANCE_STATUS:
                return produceService.updateInstanceStatus(params);

            case Activity.RELEASE_INSTANCE:
                return produceService.releaseInstance(params);

            case Activity.UPGRADE_INSTANCE:
                return produceService.upgradeInstance(params);
            case Activity.CHANGE_INSTANCE_CHECK:
                return produceService.changeInstanceCheck(params);
            default:
                return ResultCodeEnum.PARAM_ERROR.toResponse("Invalid activity");
        }
    }
}