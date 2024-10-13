package com.miirso.shortlink.project.dto.req;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.miirso.shortlink.project.dao.entity.ShortLinkDO;
import lombok.Data;

/**
 * @Package com.miirso.shortlink.project.dto.req
 * @Author miirso
 * @Date 2024/10/13 19:23
 */


@Data
public class ShortLinkPageReqDTO extends Page<ShortLinkDO> {

    /**
     * 分组标识
     */
    private String gid;

}
