package com.miirso.shortlink.admin.common.convention.errorcode;

/**
 * @Package com.miirso.shortlink.admin.common.convention.errorcode
 * @Author miirso
 * @Date 2024/10/10 23:54
 *
 * 平台错误码
 *
 */

public interface IErrorCode {

    /**
     * 错误码
     */
    String code();

    /**
     * 错误信息
     */
    String message();
}