package com.miirso.shortlink.project.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @Package com.miirso.shortlink.project.common.enums
 * @Author miirso
 * @Date 2024/10/16 22:16
 *
 * 有效期类型枚举
 *
 */

@RequiredArgsConstructor
public enum VailDateTypeEnum {

    PERMANENT(0),

    CUSTOM(1);

    @Getter
    private final Integer type;
}
