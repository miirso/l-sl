package com.miirso.shortlink.project.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Package com.miirso.shortlink.project.dao.entity
 * @Author miirso
 * @Date 2024/10/19 15:54
 *
 * 短链接访问监控统计-基础访问
 *
 */
@Data
@TableName("t_link_access_stats")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LinkAccessStatsDO extends BaseDO {

    private static final long serialVersionUID = 1L;

    /**
     * 分组标识
     */
    private String gid;

    /**
     * 完整短链接
     */
    private String fullShortUrl;

    /**
     * 日期
     */
    private Date date;

    /**
     * 访问量
     */
    private Integer pv;

    /**
     * 独立访问数
     */
    private Integer uv;

    /**
     * 独立ip数
     */
    private Integer uip;

    /**
     * 小时
     */
    private Integer hour;

    /**
     * 星期
     */
    private Integer weekday;

}
