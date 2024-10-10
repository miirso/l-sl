package com.miirso.shortlink.admin.common.enums;

import com.miirso.shortlink.admin.common.convention.errorcode.IErrorCode;
import lombok.AllArgsConstructor;

/**
 * @Package com.miirso.shortlink.admin.common.enums
 * @Author miirso
 * @Date 2024/10/11 0:32
 *
 * 用户异常码
 *
 */

@AllArgsConstructor
public enum UserErrorCode implements IErrorCode {

    USER_NULL("B000200", "用户记录不存在"),

    USER_EXIST("B000201", "用户已存在");

    private String code;

    private String message;

    @Override
    public String code() {
        return this.code;
    }

    @Override
    public String message() {
        return this.message;
    }
}
