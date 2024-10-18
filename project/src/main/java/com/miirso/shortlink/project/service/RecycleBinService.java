package com.miirso.shortlink.project.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.miirso.shortlink.project.dao.entity.ShortLinkDO;
import com.miirso.shortlink.project.dto.req.RecycleBinPageReqDTO;
import com.miirso.shortlink.project.dto.req.RecycleBinRecoverReqDTO;
import com.miirso.shortlink.project.dto.req.RecycleBinSaveReqDTO;
import com.miirso.shortlink.project.dto.resp.ShortLinkPageRespDTO;

/**
 * @Package com.miirso.shortlink.project.service
 * @Author miirso
 * @Date 2024/10/17 20:34
 */
public interface RecycleBinService extends IService<ShortLinkDO> {

    /**
     * 短链接回收站新增
     * @param recycleBinSaveReqDTO
     */
    void saveRecycleBin(RecycleBinSaveReqDTO recycleBinSaveReqDTO);

    /**
     * 分页查询短链接
     * @param reqDTO
     */
    IPage<ShortLinkPageRespDTO> pageShortLink(RecycleBinPageReqDTO reqDTO);

    /**
     * 回收站移除指定短链接接口
     * @param requestParam
     * @return
     */
    void recoverShortLink(RecycleBinRecoverReqDTO requestParam);
}
