package com.miirso.shortlink.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.miirso.shortlink.project.dao.entity.ShortLinkDO;
import com.miirso.shortlink.project.dto.req.ShortLinkCreateReqDTO;
import com.miirso.shortlink.project.dto.resp.ShortLinkCreateRespDTO;

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
}