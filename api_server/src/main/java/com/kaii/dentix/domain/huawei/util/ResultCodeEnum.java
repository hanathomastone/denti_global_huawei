package com.kaii.dentix.domain.huawei.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum ResultCodeEnum {

    SUCCESS("000000", "success"),
    ASYNC("000004", "async"),
    PARAM_ERROR("000500", "parameter error");

    private final String code;
    private final String msg;

    ResultCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Map<String, Object> toResponse(Object data) {
        Map<String, Object> map = new HashMap<>();
        map.put("resultCode", code);
        map.put("resultMsg", msg);
        if (data instanceof String) map.put("instanceId", data);
        if (data instanceof List) map.put("info", data);
        return map;
    }
}