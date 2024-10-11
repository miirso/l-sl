package com.miirso.shortlink.admin.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Package com.miirso.shortlink.admin.dto.resp
 * @Author miirso
 * @Date 2024/10/11 9:28
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRespDTO {

    /**
     * redis token
     */
    private String token;

}
