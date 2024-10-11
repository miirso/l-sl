package com.miirso.shortlink.admin.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.miirso.shortlink.admin.dao.entity.GroupDO;
import com.miirso.shortlink.admin.dao.mapper.GroupMapper;
import com.miirso.shortlink.admin.dto.req.GroupSaveReqDTO;
import com.miirso.shortlink.admin.service.GroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Package com.miirso.shortlink.admin.service.impl
 * @Author miirso
 * @Date 2024/10/11 16:46
 */

@Slf4j
@Service
public class GroupServiceImpl extends ServiceImpl<GroupMapper, GroupDO> implements GroupService {

    @Override
    public void saveGroup(GroupSaveReqDTO groupSaveReqDTO) {
        GroupDO queryDO = new GroupDO();
        String gid = null;
        while (queryDO != null) {
            gid = RandomUtil.randomString(6);
            LambdaQueryWrapper<GroupDO> queryWrapper = Wrappers.lambdaQuery(GroupDO.class)
                    .eq(GroupDO::getGid, gid)
                    // TODO 设置用户名
                    .eq(GroupDO::getUsername, null);
            queryDO = baseMapper.selectOne(queryWrapper);
        }
        GroupDO groupDO = GroupDO.builder()
                .gid(gid)
                .name(groupSaveReqDTO.getName())
                .sortOrder(0)
                .build();
        baseMapper.insert(groupDO);
    }
}
