package com.miirso.shortlink.admin.remote.dto.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.miirso.shortlink.admin.dao.entity.BaseDO;
import lombok.Data;

import java.util.Date;

/**
 * @Package com.miirso.shortlink.project.dao.entity
 * @Author miirso
 * @Date 2024/10/13 15:09
 *
 * 短链接DO Entity
 *
 */

@TableName("t_link")
@Data
public class ShortLinkDO extends BaseDO {

    /**
     * id
     */
    private Long id;

    /**
     * 域名
     */
    private String domain;

    /**
     * 短链接
     */
    private String shortUri;

    /**
     * 完整短链接
     */
    private String fullShortUrl;

    /**
     * 原始链接
     */
    private String originUrl;

    /**
     * 网站图标
     */
    private String favicon;

    /**
     * 点击量
     */
    private Integer clickNum;

    /**
     * 分组标识
     */
    private String gid;

    /**
     * 启用标识 0：未启用 1：已启用
     */
    private int enableStatus;

    /**
     * 创建类型 0：控制台 1：接口
     */
    private int createdType;

    /**
     * 有效期类型 0：永久有效 1：用户自定义
     */
    private int validDateType;

    /**
     * 有效期
     */
    private Date validDate;

    /**
     * 描述
     * 涉及到 MySQL 关键字应该使用 TableField 注解包一下
     */
    @TableField("`describe`")
    private String describe;

}


