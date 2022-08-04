package com.ling.educms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ling.commonutils.R;
import com.ling.educms.entity.CrmBanner;
import com.ling.educms.entity.vo.BannerQuery;
import com.ling.educms.service.CrmBannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.ParameterResolutionDelegate;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-03-29
 */
@RestController
@RequestMapping("/educms/banneradmin")
@Api("后台管理系统Banner列表")
@CrossOrigin
public class BannerAdminController {

    @Autowired
    private CrmBannerService crmBannerService;

    //条件分页查询banner
    @ApiOperation("获取banner分页列表")
    @PostMapping("/pageBanner/{page}/{limit}")
    public R pageBanner(@PathVariable long page, @PathVariable long limit,
                        @RequestBody(required = false) BannerQuery bannerQuery){

        Page<CrmBanner> pageBanner = new Page<>(page,limit);
        crmBannerService.page(pageBanner,null);


        return R.ok().data("items",pageBanner.getRecords()).data("total",pageBanner.getTotal());

    }

    //添加banner
    @PostMapping("/addBanner")
    public R addBanner(@RequestBody CrmBanner crmBanner){
        boolean flag = crmBannerService.save(crmBanner);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }

    //根据id删除banner
    @ApiOperation(value = "删除Banner")
    @DeleteMapping("/deleteBannerById/{id}")
    public R deleteBannerById(@PathVariable String id){
        boolean flag = crmBannerService.removeById(id);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }

    //根据id查询banner
    @GetMapping("/getBannerById/{id}")
    public R getBannerById(@PathVariable String id){
        CrmBanner crmBanner = crmBannerService.getById(id);
        return R.ok().data("item",crmBanner);
    }

}

