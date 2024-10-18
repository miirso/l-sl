package com.miirso.shortlink.admin.controller;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.miirso.shortlink.admin.client.LinkClient;
import com.miirso.shortlink.admin.common.convention.exception.ClientException;
import com.miirso.shortlink.admin.common.convention.result.Result;
import com.miirso.shortlink.admin.common.convention.result.Results;
import com.miirso.shortlink.admin.dao.entity.GroupDO;
import com.miirso.shortlink.admin.dao.mapper.GroupMapper;
import com.miirso.shortlink.admin.remote.dto.req.RecycleBinSaveReqDTO;
import com.miirso.shortlink.admin.remote.dto.req.ShortLinkPageReqDTO;
import com.miirso.shortlink.admin.remote.dto.resp.ShortLinkPageRespDTO;
import com.miirso.shortlink.admin.utils.UserHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Package com.miirso.shortlink.admin.controller
 * @Author miirso
 * @Date 2024/10/18 16:03
 */

@RestController
@RequestMapping("/api/short-link/admin/v1/recycle-bin")
@RequiredArgsConstructor
public class RecycleBinController {

    private final LinkClient linkClient;

    private final GroupMapper groupMapper;

    @PostMapping("/save")
    public Result<Void> saveRecycleBin(@RequestBody RecycleBinSaveReqDTO recycleBinSaveReqDTO) {
        linkClient.saveRecycleBin(recycleBinSaveReqDTO);
        return Results.success();
    }

    @GetMapping("/page")
    public Result<Page<ShortLinkPageRespDTO>> pageRecycledShortLink(ShortLinkPageReqDTO shortLinkPageReqDTO) {

        // 没想到要在这里查询
        // 但是也没法在link-service里添加查询代码了，毕竟要解耦合
        LambdaQueryWrapper<GroupDO> queryWrapper = Wrappers.lambdaQuery(GroupDO.class)
                .eq(GroupDO::getUsername, UserHolder.getUserInfoDTO().getUsername())
                .eq(GroupDO::getDelFlag, 0);
        List<GroupDO> groupDOS = groupMapper.selectList(queryWrapper);
        if (CollUtil.isEmpty(groupDOS)) {
            throw new ClientException("当前用户暂无分组");
        }
        List<String> gidList = groupDOS.stream().map(GroupDO::getGid).toList();

        Result<Page<ShortLinkPageRespDTO>> pageResult = linkClient.pageRecycledShortLink(
                gidList,
                shortLinkPageReqDTO.getCurrent(),
                shortLinkPageReqDTO.getSize(),
                shortLinkPageReqDTO.getOrderTag()
        );
        return pageResult;
    }

    @PostMapping("/recover")
    public Result<Void> recoverShortLink(@RequestBody RecycleBinSaveReqDTO recycleBinSaveReqDTO) {
        linkClient.recoverShortLink(recycleBinSaveReqDTO);
        return Results.success();
    }
}
