package com.miirso.shortlink.project.dto.resp;

import lombok.Data;

/**
 * @Package com.miirso.shortlink.project.dto.resp
 * @Author miirso
 * @Date 2024/10/15 15:34
 *
 * 分组中短链接数量查询返回DTO
 *
 */
@Data
public class ShortLinkGroupCountRespDTO {

    private String gid;

    private Integer shortLinkCount;

}
