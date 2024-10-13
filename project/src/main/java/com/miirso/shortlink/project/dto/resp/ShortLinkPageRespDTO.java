package com.miirso.shortlink.project.dto.resp;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

/**
 * @Package com.miirso.shortlink.project.dto.resp
 * @Author miirso
 * @Date 2024/10/13 19:24
 */

@Data
public class ShortLinkPageRespDTO {

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
     * 点击量
     */
    private Integer clickNum;

    /**
     * 分组标识
     */
    private String gid;

    /**
     * 网站icon
     */
    private String favicon;

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
