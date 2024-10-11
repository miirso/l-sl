package com.miirso.shortlink.admin.utils;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordEncoder {
    // MD5已被证明不安全
    // 使用BCrypt加密
    public static String encode(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    // 验证密码
    public static Boolean matches(String encodedPassword, String rawPassword) {
        if (encodedPassword == null || rawPassword == null) {
            return false;
        }
        return BCrypt.checkpw(rawPassword, encodedPassword);
    }
}
