package com.miirso.shortlink.project.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Package com.miirso.shortlink.project.dao.entity
 * @Author miirso
 * @Date 2024/10/17 17:27
 *
 * 短链接跳转实体
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_link_goto")
public class ShortLinkGotoDO {

    /**
     * ID
     */
    private Long id;

    /**
     * 分组标识
     */
    private String gid;

    /**
     * 完整短链接
     */
    private String fullShortUrl;

}
