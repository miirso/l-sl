package com.miirso.shortlink.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.miirso.shortlink.admin.dao.entity.GroupDO;
import com.miirso.shortlink.admin.dao.mapper.GroupMapper;
import com.miirso.shortlink.admin.dto.req.GroupSaveReqDTO;
import com.miirso.shortlink.admin.dto.req.GroupUpdateReqDTO;
import com.miirso.shortlink.admin.dto.resp.GroupRespDTO;
import com.miirso.shortlink.admin.service.GroupService;
import com.miirso.shortlink.admin.utils.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

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
        String username = UserHolder.getUserInfoDTO().getUsername();
        String gid = null;
        while (queryDO != null) {
            gid = RandomUtil.randomString(6);
            LambdaQueryWrapper<GroupDO> queryWrapper = Wrappers.lambdaQuery(GroupDO.class)
                    .eq(GroupDO::getGid, gid)
                    .eq(GroupDO::getName, groupSaveReqDTO.getName()); // 貌似是可重复的.
            queryDO = baseMapper.selectOne(queryWrapper);
        }
        GroupDO groupDO = GroupDO.builder()
                .gid(gid)
                .name(groupSaveReqDTO.getName())
                .username(username)
                .sortOrder(0)
                .build();
        groupDO.setDelFlag(false);
        baseMapper.insert(groupDO);
    }

    @Override
    public List<GroupRespDTO> listGroup() {

        String username = UserHolder.getUserInfoDTO().getUsername();
        LambdaQueryWrapper<GroupDO> groupDOLambdaQueryWrapper = Wrappers.lambdaQuery(GroupDO.class)
                .eq(GroupDO::getUsername, username)
                .eq(GroupDO::getDelFlag, 0)
                .orderByDesc(GroupDO::getSortOrder, GroupDO::getUpdateTime);
        List<GroupDO> groupDOS = baseMapper.selectList(groupDOLambdaQueryWrapper);
        return BeanUtil.copyToList(groupDOS, GroupRespDTO.class);
    }

    @Override
    public void updateGroup(GroupUpdateReqDTO groupUpdateReqDTO) {
        LambdaUpdateWrapper<GroupDO> updateWrapper = Wrappers.lambdaUpdate(GroupDO.class)
                .eq(GroupDO::getGid, groupUpdateReqDTO.getGid())
                .eq(GroupDO::getDelFlag, 0);
        GroupDO groupDO = new GroupDO();
        groupDO.setName(groupUpdateReqDTO.getName());
        baseMapper.update(groupDO, updateWrapper);
    }
}
