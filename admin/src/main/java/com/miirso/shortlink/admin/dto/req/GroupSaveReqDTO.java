package com.miirso.shortlink.admin.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Package com.miirso.shortlink.admin.dto.req
 * @Author miirso
 * @Date 2024/10/11 16:56
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupSaveReqDTO {

    /**
     * 分组名称
     */
    private String name;

}
