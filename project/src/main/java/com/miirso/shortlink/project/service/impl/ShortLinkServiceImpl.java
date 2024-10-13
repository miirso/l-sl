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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Package com.miirso.shortlink.project.service.impl
 * @Author miirso
 * @Date 2024/10/13 15:16
 *
 *
 *
 */

@Slf4j
@Service
public class ShortLinkServiceImpl extends ServiceImpl<ShortLinkMapper, ShortLinkDO> implements ShortLinkService {

    @Override
    public ShortLinkCreateRespDTO createShortLink(ShortLinkCreateReqDTO reqDTO) {

        String shortLinkSuffix = generateSuffix(reqDTO.getOriginUrl(), reqDTO.getDomain());
        ShortLinkDO shortLinkDO = BeanUtil.toBean(reqDTO, ShortLinkDO.class);
        shortLinkDO.setFullShortUrl(reqDTO.getDomain() + "/" + shortLinkSuffix);
        shortLinkDO.setShortUri(shortLinkSuffix);
        baseMapper.insert(shortLinkDO);
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
            shortUri = HashUtil.hashToBase62(url);
            // 采用数据库查询的话，会直接造成数据库压力过大，这里肯定是需要套一层查询隔离在数据库外
            LambdaQueryWrapper<ShortLinkDO> shortUriQueryWrapper = Wrappers.lambdaQuery(ShortLinkDO.class)
                    .eq(ShortLinkDO::getFullShortUrl, domain + '/' + shortUri);
            ShortLinkDO queryRes = baseMapper.selectOne(shortUriQueryWrapper);
            if (queryRes == null)
                break;
            customGenerateCount++;
        }

        return shortUri;
    }
}
