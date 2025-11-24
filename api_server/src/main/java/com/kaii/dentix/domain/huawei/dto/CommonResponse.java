package com.kaii.dentix.domain.huawei.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommonResponse {
    private String result; // success / fail
}