package com.miirso.shortlink.admin.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Package com.miirso.shortlink.admin.dto.resp
 * @Author miirso
 * @Date 2024/10/11 4:20
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginRespDTO {

    /**
     * 老生常谈用户token
     */
    private String token;


}
