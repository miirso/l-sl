package com.miirso.shortlink.admin.remote.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Package com.miirso.shortlink.project.dto.resp
 * @Author miirso
 * @Date 2024/10/20 21:08
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShortLinkStatsOsRespDTO {

    /**
     * 统计
     */
    private Integer cnt;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 占比
     */
    private Double ratio;

}
