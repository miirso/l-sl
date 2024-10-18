package com.miirso.shortlink.admin.client;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.miirso.shortlink.admin.common.convention.result.Result;
import com.miirso.shortlink.admin.dto.resp.ShortLinkGroupCountRespDTO;
import com.miirso.shortlink.admin.remote.dto.req.RecycleBinSaveReqDTO;
import com.miirso.shortlink.admin.remote.dto.req.ShortLinkCreateReqDTO;
import com.miirso.shortlink.admin.remote.dto.req.ShortLinkUpdateReqDTO;
import com.miirso.shortlink.admin.remote.dto.resp.ShortLinkCreateRespDTO;
import com.miirso.shortlink.admin.remote.dto.resp.ShortLinkPageRespDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    Result<ShortLinkCreateRespDTO> createShortLink(@RequestBody ShortLinkCreateReqDTO reqDTO);

    /**
     * 远程调用 link-service 的分组查询接口
     * @param gids
     */
    @GetMapping("/api/short-link/project/v1/count")
    Result<List<ShortLinkGroupCountRespDTO>> listGroupShortLinkCount(@RequestParam("gids") List<String> gids);

    /**
     * 远程调用 link-service 的修改短链接信息功能
     * @param requestParam
     * @return
     */
    @RequestMapping(value = "/api/short-link/project/v1/update"
    , method = RequestMethod.POST)
    Result<Void> updateShortLink(@RequestBody ShortLinkUpdateReqDTO requestParam);

    /**
     * 远程调用 link-service 的回收站新增功能
     * @param recycleBinSaveReqDTO
     * @return
     */
    @PostMapping("/api/short-link/project/v1/recycle-bin/save")
    Result<Void> saveRecycleBin(@RequestBody RecycleBinSaveReqDTO recycleBinSaveReqDTO);

    /**
     * 远程调用 link-service 的分页查询回收站功能
     * @param current
     * @param size
     * @param orderTag
     */
    @GetMapping("/api/short-link/project/v1/recycle-bin/page")
    Result<Page<ShortLinkPageRespDTO>> pageRecycledShortLink(
            @RequestParam(name = "gidList", required = true) List<String> gidList,
            @RequestParam(name = "current", required = true) Long current,
            @RequestParam(name = "size", required = true) Long size,
            @RequestParam(name = "orderTag", required = false) String orderTag
    );
}
