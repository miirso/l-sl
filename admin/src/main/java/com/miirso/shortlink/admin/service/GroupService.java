package com.miirso.shortlink.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.miirso.shortlink.admin.dao.entity.GroupDO;
import com.miirso.shortlink.admin.dto.req.GroupSaveReqDTO;
import com.miirso.shortlink.admin.dto.resp.GroupRespDTO;

import java.util.List;

/**
 * @Package com.miirso.shortlink.admin.service
 * @Author miirso
 * @Date 2024/10/11 16:46
 *
 * 短链接分组接口层
 *
 */

public interface GroupService extends IService<GroupDO> {

    /**
     * 新增短链接分组
     * @param groupSaveReqDTO
     */
    void saveGroup(GroupSaveReqDTO groupSaveReqDTO);

    /**
     * 查询短链接分组列表
     * @return List<GroupRespDTO>
     */
    List<GroupRespDTO> listGroup();

}
