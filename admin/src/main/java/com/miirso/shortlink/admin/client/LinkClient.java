package com.miirso.shortlink.admin.client;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.miirso.shortlink.admin.common.convention.result.Result;
import com.miirso.shortlink.admin.remote.dto.req.ShortLinkCreateReqDTO;
import com.miirso.shortlink.admin.remote.dto.resp.ShortLinkCreateRespDTO;
import com.miirso.shortlink.admin.remote.dto.resp.ShortLinkPageRespDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Package com.miirso.shortlink.admin.client
 * @Author miirso
 * @Date 2024/10/13 23:14
 *
 * OpenFeign 客户端
 * 按照下面这样声明接口，OpenFeign就可以动态代理实现这个方法。
 */

@FeignClient(name = "link-service", url = "http://127.0.0.1:8001") // 声明调用服务的名称和url
public interface LinkClient {

    /**
     * 远程调用link-service服务的分组查询短链接接口
     * @param gid
     * @param current
     * @param size
     * @param orderTag
     * <p>
     * 这里不采用ShortLinkPageReqDTO作为参数，如果不采用RequestParam注解的方式传递参数的话，Feign会自动将GET方法转换成POST方法。
     * </p>
     */
    @GetMapping("/api/short-link/project/v1/page")
    Result<Page<ShortLinkPageRespDTO>> pageShortLink(
            @RequestParam(name = "gid", required = true) String gid,
            @RequestParam(name = "current", required = true) Long current,
            @RequestParam(name = "size", required = true) Long size,
            @RequestParam(name = "orderTag", required = false) String orderTag
    );

    /**
     * 远程调用link-service的创建新链接接口
     * @param reqDTO
     */
    @PostMapping("/api/short-link/project/v1/create")
    public Result<ShortLinkCreateRespDTO> createShortLink(@RequestBody ShortLinkCreateReqDTO reqDTO);

}
