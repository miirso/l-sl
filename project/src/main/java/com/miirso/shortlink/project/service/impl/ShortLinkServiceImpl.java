package com.miirso.shortlink.project.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.miirso.shortlink.project.common.convention.exception.ClientException;
import com.miirso.shortlink.project.common.convention.exception.ServiceException;
import com.miirso.shortlink.project.common.enums.VailDateTypeEnum;
import com.miirso.shortlink.project.dao.entity.ShortLinkDO;
import com.miirso.shortlink.project.dao.entity.ShortLinkGotoDO;
import com.miirso.shortlink.project.dao.mapper.ShortLinkGotoMapper;
import com.miirso.shortlink.project.dao.mapper.ShortLinkMapper;
import com.miirso.shortlink.project.dto.req.ShortLinkCreateReqDTO;
import com.miirso.shortlink.project.dto.req.ShortLinkPageReqDTO;
import com.miirso.shortlink.project.dto.req.ShortLinkUpdateReqDTO;
import com.miirso.shortlink.project.dto.resp.ShortLinkCreateRespDTO;
import com.miirso.shortlink.project.dto.resp.ShortLinkGroupCountRespDTO;
import com.miirso.shortlink.project.dto.resp.ShortLinkPageRespDTO;
import com.miirso.shortlink.project.service.ShortLinkService;
import com.miirso.shortlink.project.utils.HashUtil;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.miirso.shortlink.project.common.constant.RedisKeyConstant.GOTO_SHORT_LINK_KEY;
import static com.miirso.shortlink.project.common.constant.RedisKeyConstant.LOCK_GOTO_SHORT_LINK_KEY;

/**
 * @Package com.miirso.shortlink.project.service.impl
 * @Author miirso
 * @Date 2024/10/13 15:16
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class ShortLinkServiceImpl extends ServiceImpl<ShortLinkMapper, ShortLinkDO> implements ShortLinkService {

    // TODO
    // 针对过滤器容量打满的问题，可以设置一个定时任务尝试在满时更新过滤器容量
    private final RBloomFilter<String> shortUriCreateCachePenetrationBloomFilter;

    private final ShortLinkMapper shortLinkMapper;

    private final ShortLinkGotoMapper shortLinkGotoMapper;

    private final StringRedisTemplate stringRedisTemplate;

    private final RedissonClient redissonClient;

    @Override
    public ShortLinkCreateRespDTO createShortLink(ShortLinkCreateReqDTO reqDTO) {

        String shortLinkSuffix = generateSuffix(reqDTO.getOriginUrl(), reqDTO.getDomain());
        String fullShortLinkUrl = reqDTO.getDomain() + "/" + shortLinkSuffix;
        ShortLinkDO shortLinkDO = BeanUtil.toBean(reqDTO, ShortLinkDO.class);
        shortLinkDO.setFullShortUrl(fullShortLinkUrl);
        shortLinkDO.setShortUri(shortLinkSuffix);
        // shortLinkDO.setDelFlag(0);

        ShortLinkGotoDO shortLinkGotoDO = ShortLinkGotoDO.builder()
                .fullShortUrl(fullShortLinkUrl)
                .gid(reqDTO.getGid())
                .build();

        try {
            baseMapper.insert(shortLinkDO);
            log.info("Successfully inserted ShortLinkDO: {}", shortLinkDO);

            shortLinkGotoMapper.insert(shortLinkGotoDO);
            log.info("Successfully inserted ShortLinkGotoDO: {}", shortLinkGotoDO);
        } catch (DuplicateKeyException e) {
            // 针对布隆过滤器误判的报错
            // KEYPOINT
            // 套一层数据库查询
            // LambdaQueryWrapper<ShortLinkDO> queryWrapper = Wrappers.lambdaQuery(ShortLinkDO.class)
            //         .eq(ShortLinkDO::getFullShortUrl, fullShortLinkUrl);
            // ShortLinkDO hasShortLinkDO = baseMapper.selectOne(queryWrapper);
            // if (hasShortLinkDO != null) {
            //     log.warn("短链接: {} 重复入库.", shortLinkDO.getFullShortUrl());
            //     throw new ServiceException("短链接生成重复");
            // }

            // 首先判断是否存在布隆过滤器，如果不存在直接新增
            String fullShortUrl = shortLinkDO.getFullShortUrl();
            if (!shortUriCreateCachePenetrationBloomFilter.contains(fullShortUrl)) {
                shortUriCreateCachePenetrationBloomFilter.add(fullShortUrl);
            }
            throw new ServiceException(String.format("短链接：%s 生成重复", fullShortUrl));
        }
        shortUriCreateCachePenetrationBloomFilter.add(shortLinkSuffix);
        return ShortLinkCreateRespDTO.builder()
                .gid(shortLinkDO.getGid())
                .originUrl(shortLinkDO.getOriginUrl())
                .fullShortUrl(shortLinkDO.getFullShortUrl())
                .build();
    }

    private String generateSuffix(String url, String domain) {
        // 存在冲突的可能性
        int customGenerateCount = 0;
        String shortUri;
        while (true) {
            if (customGenerateCount > 10)
                throw new ServiceException("短链接生成次数过多，wait a minute");

            // KEYPOINT
            // 对生成短链的原始链接拼接当前毫秒数,这样可以避免相同原链生成的短链冲突.
            shortUri = HashUtil.hashToBase62(url + System.currentTimeMillis());

            String fullShortLink = domain + '/' + shortUri;

            if (!shortUriCreateCachePenetrationBloomFilter.contains(fullShortLink)) {
                // 如果布隆过滤器误判怎么办？
                // baseMapper执行insert时，会对索引fullShortLink的重复报错
                break;
            }
            // 采用数据库查询的话，会直接造成数据库压力过大，这里肯定是需要套一层查询隔离在数据库外
            // LambdaQueryWrapper<ShortLinkDO> shortUriQueryWrapper = Wrappers.lambdaQuery(ShortLinkDO.class)
            //         .eq(ShortLinkDO::getFullShortUrl, fullShortLink);
            // ShortLinkDO queryRes = baseMapper.selectOne(shortUriQueryWrapper);
            // if (queryRes == null)
            //     break;
            customGenerateCount++;
        }

        return shortUri;
    }

    @Override
    public IPage<ShortLinkPageRespDTO> pageShortLink(ShortLinkPageReqDTO shortLinkPageReqDTO) {
        LambdaQueryWrapper<ShortLinkDO> queryWrapper = Wrappers.lambdaQuery(ShortLinkDO.class)
                .eq(ShortLinkDO::getGid, shortLinkPageReqDTO.getGid())
                .eq(ShortLinkDO::getDelFlag, 0)
                .eq(ShortLinkDO::getEnableStatus, 0)
                .orderByDesc(ShortLinkDO::getCreateTime);
        IPage<ShortLinkDO> resultPage = baseMapper.selectPage(shortLinkPageReqDTO, queryWrapper);
        return resultPage.convert(each -> BeanUtil.toBean(each, ShortLinkPageRespDTO.class));
    }

    @Override
    public List<ShortLinkGroupCountRespDTO> listGroupShortLinkCount(List<String> gids) {
        QueryWrapper<ShortLinkDO> shortLinkDOQueryWrapper = Wrappers.query(new ShortLinkDO())
                .select("gid as gid, count(*) as shortLinkCount")
                .in("gid", gids)
                .eq("enable_status", 0)
                .groupBy("gid");
        List<Map<String, Object>> shortLinkDOList = baseMapper.selectMaps(shortLinkDOQueryWrapper);
        return BeanUtil.copyToList(shortLinkDOList, ShortLinkGroupCountRespDTO.class);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateShortLink(ShortLinkUpdateReqDTO requestParam) {
        LambdaQueryWrapper<ShortLinkDO> queryWrapper = Wrappers.lambdaQuery(ShortLinkDO.class)
                .eq(ShortLinkDO::getGid, requestParam.getGid())
                .eq(ShortLinkDO::getFullShortUrl, requestParam.getFullShortUrl())
                .eq(ShortLinkDO::getDelFlag, 0)
                .eq(ShortLinkDO::getEnableStatus, 0);
        ShortLinkDO hasShortLinkDO = baseMapper.selectOne(queryWrapper);
        if (hasShortLinkDO == null) {
            throw new ClientException("短链接记录不存在");
        }
        ShortLinkDO shortLinkDO = ShortLinkDO.builder()
                .domain(hasShortLinkDO.getDomain())
                .shortUri(hasShortLinkDO.getShortUri())
                .clickNum(hasShortLinkDO.getClickNum())
                .favicon(hasShortLinkDO.getFavicon())
                .createdType(hasShortLinkDO.getCreatedType())
                .gid(requestParam.getGid())
                .originUrl(requestParam.getOriginUrl())
                .describe(requestParam.getDescribe())
                .validDateType(requestParam.getValidDateType())
                .validDate(requestParam.getValidDate())
                .build();
        if (Objects.equals(hasShortLinkDO.getGid(), requestParam.getGid())) {
            LambdaUpdateWrapper<ShortLinkDO> updateWrapper = Wrappers.lambdaUpdate(ShortLinkDO.class)
                    .eq(ShortLinkDO::getFullShortUrl, requestParam.getFullShortUrl())
                    .eq(ShortLinkDO::getGid, requestParam.getGid())
                    .eq(ShortLinkDO::getDelFlag, 0)
                    .eq(ShortLinkDO::getEnableStatus, 0)
                    .set(Objects.equals(requestParam.getValidDateType(), VailDateTypeEnum.PERMANENT.getType()), ShortLinkDO::getValidDate, null);
            baseMapper.update(shortLinkDO, updateWrapper);
        } else {
            LambdaUpdateWrapper<ShortLinkDO> updateWrapper = Wrappers.lambdaUpdate(ShortLinkDO.class)
                    .eq(ShortLinkDO::getFullShortUrl, requestParam.getFullShortUrl())
                    .eq(ShortLinkDO::getGid, hasShortLinkDO.getGid())
                    .eq(ShortLinkDO::getDelFlag, 0)
                    .eq(ShortLinkDO::getEnableStatus, 0);
            baseMapper.delete(updateWrapper);
            baseMapper.insert(shortLinkDO);
        }
    }


    @SneakyThrows
    @Override
    public void restoreUrl(String shortUri, ServletRequest servletRequest, ServletResponse servletResponse) {
        String serverName = servletRequest.getServerName(); // 处理当前请求的服务器主机名:接收主机名
        String fullShortUrl = serverName + '/' + shortUri;
        // Redis 中获取原始链接
        String originalUrl = stringRedisTemplate.opsForValue().get(GOTO_SHORT_LINK_KEY + fullShortUrl);


        if (StrUtil.isNotBlank(originalUrl)) {
            ((HttpServletResponse)servletResponse).sendRedirect(originalUrl);
            return;
        }

        RLock lock = redissonClient.getLock(LOCK_GOTO_SHORT_LINK_KEY + fullShortUrl);
        lock.lock();

        try {
            originalUrl = stringRedisTemplate.opsForValue().get(GOTO_SHORT_LINK_KEY + fullShortUrl);
            if (StrUtil.isNotBlank(originalUrl)) {
                    ((HttpServletResponse)servletResponse).sendRedirect(originalUrl);
                    return;
            }
            LambdaQueryWrapper<ShortLinkGotoDO> linkGotoQueryWrapper = Wrappers.lambdaQuery(ShortLinkGotoDO.class)
                    .eq(ShortLinkGotoDO::getFullShortUrl, fullShortUrl);
            ShortLinkGotoDO shortLinkGotoDO = shortLinkGotoMapper.selectOne(linkGotoQueryWrapper);
            String gid = shortLinkGotoDO.getGid();
            if (gid == null) {return;}
            LambdaQueryWrapper<ShortLinkDO> queryWrapper = Wrappers.lambdaQuery(ShortLinkDO.class)
                    .eq(ShortLinkDO::getGid, gid)
                    .eq(ShortLinkDO::getFullShortUrl, fullShortUrl)
                    .eq(ShortLinkDO::getDelFlag, 0)
                    .eq(ShortLinkDO::getEnableStatus, 0);
            ShortLinkDO shortLinkDO = baseMapper.selectOne(queryWrapper);
            // 实现跳转
            if (shortLinkDO == null) {throw new ClientException("无此短链接");}
                    stringRedisTemplate.opsForValue().set(GOTO_SHORT_LINK_KEY + fullShortUrl, shortLinkDO.getOriginUrl());
                    ((HttpServletResponse)servletResponse).sendRedirect(shortLinkDO.getOriginUrl());
        } finally {
            lock.unlock();
        }

    }
}


// originalUrl = stringRedisTemplate.opsForValue().get(GOTO_SHORT_LINK_KEY + fullShortUrl);
//                 if (StrUtil.isNotBlank(originalUrl)) {
//         ((HttpServletResponse)servletResponse).sendRedirect(originalUrl);
//                 }
// LambdaQueryWrapper<ShortLinkGotoDO> linkGotoQueryWrapper = Wrappers.lambdaQuery(ShortLinkGotoDO.class)
//         .eq(ShortLinkGotoDO::getFullShortUrl, fullShortUrl);
// ShortLinkGotoDO shortLinkGotoDO = shortLinkGotoMapper.selectOne(linkGotoQueryWrapper);
// String gid = shortLinkGotoDO.getGid();
//                 if (gid == null) {return;}
// LambdaQueryWrapper<ShortLinkDO> queryWrapper = Wrappers.lambdaQuery(ShortLinkDO.class)
//         .eq(ShortLinkDO::getGid, gid)
//         .eq(ShortLinkDO::getFullShortUrl, fullShortUrl)
//         .eq(ShortLinkDO::getDelFlag, 0)
//         .eq(ShortLinkDO::getEnableStatus, 0);
// ShortLinkDO shortLinkDO = baseMapper.selectOne(queryWrapper);
// // 实现跳转
//                 if (shortLinkDO == null) {throw new ClientException("无此短链接");}
//         stringRedisTemplate.opsForValue().set(GOTO_SHORT_LINK_KEY + fullShortUrl, shortLinkDO.getOriginUrl());
//         ((HttpServletResponse)servletResponse).sendRedirect(shortLinkDO.getOriginUrl());








































