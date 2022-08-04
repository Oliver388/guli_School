package com.ling.educms.controller;


import com.ling.commonutils.R;
import com.ling.educms.entity.CrmBanner;
import com.ling.educms.service.CrmBannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-03-29
 */
@RestController
@RequestMapping("/educms/bannerfront")
@Api("网站首页Banner列表")
@CrossOrigin //跨域
public class BannerFrontController {

    @Autowired
    private CrmBannerService crmBannerService;

    //查询所有幻灯片
    @ApiOperation(value = "获取首页banner")
    @GetMapping("getAllBanner")
    public R getAll(){
        List<CrmBanner> list = crmBannerService.getAllBanner();
        return R.ok().data("list",list);
    }

}

