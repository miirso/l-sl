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

    USER_NAME_EXIST("B000201", "用户名已存在"),

    USER_EXIST("B000202", "用户已存在"),

    USER_SAVE_ERROR("B000203", "用户记录新增失败"),

    USER_NAME_ERROR("B000204", "用户名为空"),

    USER_LOGIN_ERROR("B000205", "密码错误");

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
