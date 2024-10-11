package com.miirso.shortlink.admin.dao.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

/**
 * @Package com.miirso.shortlink.admin.dao.entity
 * @Author miirso
 * @Date 2024/10/10 19:55
 *
 * DO 数据库持久层对象
 *
 */

@Data
@TableName("t_user")
public class UserDO extends BaseDO {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String mail;

    /**
     * 注销时间戳
     */
    private Long deletionTime;

    /**
     * 团队标识
     */
    private Long groupId;

    public UserDO() {}

}
