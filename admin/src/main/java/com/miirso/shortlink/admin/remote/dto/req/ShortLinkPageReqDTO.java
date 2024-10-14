package com.miirso.shortlink.admin.remote.dto.req;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.miirso.shortlink.admin.remote.dto.entity.ShortLinkDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Package com.miirso.shortlink.project.dto.req
 * @Author miirso
 * @Date 2024/10/13 19:23
 */


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShortLinkPageReqDTO extends Page<ShortLinkDO> {

    /**
     * 分组标识
     */
    private String gid;

    /**
     * 排序方式
     */
    private String orderTag;
}
