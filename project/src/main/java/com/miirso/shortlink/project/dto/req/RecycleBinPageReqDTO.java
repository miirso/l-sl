package com.miirso.shortlink.project.dto.req;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.miirso.shortlink.project.dao.entity.ShortLinkDO;
import lombok.Data;

import java.util.List;

/**
 * @Package com.miirso.shortlink.project.dto.req
 * @Author miirso
 * @Date 2024/10/18 16:56
 */
@Data
public class RecycleBinPageReqDTO extends Page<ShortLinkDO> {

    private List<String> gidList;

}
