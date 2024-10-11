package com.miirso.shortlink.admin.dao.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Package com.miirso.shortlink.admin.dao.entity
 * @Author miirso
 * @Date 2024/10/11 16:42
 *
 * 短链接分组DO
 *
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_group")
@Builder
public class GroupDO extends BaseDO {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 分组标识
     */
    private String gid;

    /**
     * 分组名称
     */
    private String name;

    /**
     * 创建分组用户名
     */
    private String username;

    /**
     * 分组排序
     */
    private Integer sortOrder;

}
