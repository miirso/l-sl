package com.miirso.shortlink.admin.controller;

import com.miirso.shortlink.admin.common.convention.result.Result;
import com.miirso.shortlink.admin.common.convention.result.Results;
import com.miirso.shortlink.admin.dto.req.GroupSaveReqDTO;
import com.miirso.shortlink.admin.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Package com.miirso.shortlink.admin.controller
 * @Author miirso
 * @Date 2024/10/11 16:48
 *
 * 短链接分组控制层
 *
 */

@RestController
@RequestMapping("/api/short-link/admin/v1/group")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    /**
     * 新增短链接分组
     * @param groupSaveReqDTO
     */
    @PostMapping
    public Result<Void> saveGroup(@RequestBody GroupSaveReqDTO groupSaveReqDTO) {
        groupService.saveGroup(groupSaveReqDTO);
        return Results.success();
    }
}
