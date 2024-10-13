package com.miirso.shortlink.admin.remote.dto.thread;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Package com.miirso.shortlink.admin.dto.thread
 * @Author miirso
 * @Date 2024/10/11 9:32
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO {

    // 注意这里是userId,而数据库表中为id.
    private Long userId;

    private String username;

    private String realName;

    private String token;

}
