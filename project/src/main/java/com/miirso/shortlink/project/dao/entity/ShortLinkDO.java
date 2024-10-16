package com.miirso.shortlink.project.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    // KEYPOINT
    // 在MyBatisPlus中，当实体类的属性是基本数据类型（如int）而不是其包装类型（如Integer）时，可能会遇到一些特殊行为，尤其是在动态SQL生成的场景中。这是因为基本数据类型不能被赋予null值，而包装类型可以。
    // 当MyBatisPlus使用AbstractWrapper（如QueryWrapper）来构建动态SQL的where条件时，它会检查实体类属性的值来决定是否将该条件加入到SQL语句中。如果属性是Integer类型且值为null，MyBatisPlus会理解为你不想根据这个字段过滤数据，因此不会在生成的SQL中包含这个条件。但如果是int类型，由于它不能为null，在实体类实例化时，默认值为0，这可能导致以下情况：
    // 如果你本意是想根据某个条件动态地决定是否添加到查询中，而这个条件对应的实体类属性是int类型，默认值0会被误解为有效的查询条件，即where子句中会包含这个字段等于0的条件。
    // 即使你不打算基于这个字段进行过滤，因为没有null的概念，该字段的默认值（通常是0）会被视为有效值并影响查询结果。
    // 因此，为了更灵活地处理可选的查询条件，推荐在实体类中使用包装类型（如Integer、Long等），这样可以利用null值来明确表示“不考虑此条件”的意图。
    /**
     * 启用标识 0：未启用 1：已启用
     */
    private Integer enableStatus;

    /**
     * 创建类型 0：控制台 1：接口
     */
    private Integer createdType;

    /**
     * 有效期类型 0：永久有效 1：用户自定义
     */
    private Integer validDateType;

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


