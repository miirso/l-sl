package com.miirso.shortlink.admin.utils;

import com.miirso.shortlink.admin.dto.thread.UserInfoDTO;

/**
 * @Package com.miirso.shortlink.admin.utils
 * @Author miirso
 * @Date 2024/10/11 9:31
 */

public class UserHolder {
    private static final ThreadLocal<UserInfoDTO> tl = new ThreadLocal<>();

    public static void saveUser(UserInfoDTO user){
        tl.set(user);
    }

    public static UserInfoDTO getUser(){
        return tl.get();
    }

    public static void removeUser(){
        tl.remove();
    }
}
