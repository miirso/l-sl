package com.miirso.shortlink.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.miirso.shortlink.project.dao.entity.ShortLinkDO;
import com.miirso.shortlink.project.dto.req.RecycleBinSaveReqDTO;

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
}
