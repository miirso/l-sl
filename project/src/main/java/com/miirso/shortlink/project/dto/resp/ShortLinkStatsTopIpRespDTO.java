package com.miirso.shortlink.project.dto.resp;

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
public class ShortLinkStatsTopIpRespDTO {

    /**
     * 统计
     */
    private Integer cnt;

    /**
     * IP
     */
    private String ip;

}
