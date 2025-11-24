package com.kaii.dentix.domain.huawei.application;

import com.kaii.dentix.domain.huawei.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;

public interface IsvProduceService {

    Object newInstance(Map<String, Object> params);

    Object queryInstance(Map<String, Object> params);

    Object refreshInstance(Map<String, Object> params);

    Object updateInstanceStatus(Map<String, Object> params);

    Object releaseInstance(Map<String, Object> params);

    Object upgradeInstance(Map<String, Object> params);
    Object changeInstanceCheck(Map<String, Object> params);
}