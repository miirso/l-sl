package com.miirso.shortlink.project.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.miirso.shortlink.project.common.convention.exception.ServiceException;
import com.miirso.shortlink.project.dao.entity.ShortLinkDO;
import com.miirso.shortlink.project.dao.mapper.ShortLinkMapper;
import com.miirso.shortlink.project.dto.req.ShortLinkCreateReqDTO;
import com.miirso.shortlink.project.dto.resp.ShortLinkCreateRespDTO;
import com.miirso.shortlink.project.service.ShortLinkService;
import com.miirso.shortlink.project.utils.HashUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

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
    private final RBloomFilter<String> shortUriCreteCachePenetrationBloomFilter;

    @Override
    public ShortLinkCreateRespDTO createShortLink(ShortLinkCreateReqDTO reqDTO) {

        String shortLinkSuffix = generateSuffix(reqDTO.getOriginUrl(), reqDTO.getDomain());
        String fullShortLinkUrl = reqDTO.getDomain() + "/" + shortLinkSuffix;
        ShortLinkDO shortLinkDO = BeanUtil.toBean(reqDTO, ShortLinkDO.class);
        shortLinkDO.setFullShortUrl(fullShortLinkUrl);
        shortLinkDO.setShortUri(shortLinkSuffix);
        try {
            baseMapper.insert(shortLinkDO);
        } catch (DuplicateKeyException e) {
            // 针对布隆过滤器误判的报错
            // KEYPOINT
            // 套一层数据库查询
            LambdaQueryWrapper<ShortLinkDO> queryWrapper = Wrappers.lambdaQuery(ShortLinkDO.class)
                    .eq(ShortLinkDO::getFullShortUrl, fullShortLinkUrl);
            ShortLinkDO hasShortLinkDO = baseMapper.selectOne(queryWrapper);
            if (hasShortLinkDO != null) {
                log.warn("短链接: {} 重复入库.", shortLinkDO.getFullShortUrl());
                throw new ServiceException("短链接生成重复");
            }
        }
        shortUriCreteCachePenetrationBloomFilter.add(shortLinkSuffix);
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

            if (!shortUriCreteCachePenetrationBloomFilter.contains(fullShortLink)) {
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
}