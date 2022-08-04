package com.ling.educms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ling.educms.entity.CrmBanner;
import com.ling.educms.mapper.CrmBannerMapper;
import com.ling.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.val;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-03-29
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    //查询所有banner
    @Override
    @Cacheable(key = "'selectIndexList'",value = "banner")
    public List<CrmBanner> getAllBanner() {
        //根据id进行降序排序，显示排序之后的前两条结果
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 2");

        List<CrmBanner>  list = baseMapper.selectList(wrapper);
        return list;
    }
}
