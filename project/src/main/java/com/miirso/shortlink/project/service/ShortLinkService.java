package com.miirso.shortlink.project.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.miirso.shortlink.project.dao.entity.ShortLinkDO;
import com.miirso.shortlink.project.dto.req.ShortLinkCreateReqDTO;
import com.miirso.shortlink.project.dto.req.ShortLinkPageReqDTO;
import com.miirso.shortlink.project.dto.resp.ShortLinkCreateRespDTO;
import com.miirso.shortlink.project.dto.resp.ShortLinkGroupCountRespDTO;
import com.miirso.shortlink.project.dto.resp.ShortLinkPageRespDTO;

import java.util.List;

/**
 * @Package com.miirso.shortlink.project.service
 * @Author miirso
 * @Date 2024/10/13 15:14
 */
public interface ShortLinkService extends IService<ShortLinkDO> {

    /**
     * 创建新的短链接
     * @param reqDTO
     */
    ShortLinkCreateRespDTO createShortLink(ShortLinkCreateReqDTO reqDTO);

    /**
     * 分页查询短链接
     * @param shortLinkPageReqDTO
     */
    IPage<ShortLinkPageRespDTO> pageShortLink(ShortLinkPageReqDTO shortLinkPageReqDTO);

    /**
     * 查询分组中短链接数量
     * @param gids
     */
    List<ShortLinkGroupCountRespDTO> listGroupShortLinkCount(List<String> gids);
}
