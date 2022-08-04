package com.ling.educenter.controller;


import com.ling.commonutils.JwtUtils;
import com.ling.commonutils.R;
import com.ling.educenter.entity.UcenterMember;
import com.ling.educenter.entity.vo.LoginVo;
import com.ling.educenter.entity.vo.RegisterVo;
import com.ling.educenter.service.UcenterMemberService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-03-31
 */
@RestController
@RequestMapping("/educenter/member")
@CrossOrigin
public class UcenterMemberController {
    @Autowired
    private UcenterMemberService memberService;

    //登录
    @ApiOperation(value = "会员登录")
    @PostMapping("/login")
    public R login(@RequestBody LoginVo loginVo){
        //返回token，使用jwt生成
        String token = memberService.login(loginVo);
        return R.ok().data("token",token);
    }

    //注册
    @ApiOperation(value = "会员注册")
    @PostMapping("/register")
    public R register(@RequestBody RegisterVo registerVo){
        memberService.register(registerVo);
        return R.ok();
    }

    //根据token获取用户信息
    @ApiOperation(value = "根据token获取登录信息")
    @GetMapping("getLoginInfo")
    public R getMemberInfo(HttpServletRequest request){
        //调用jwt工具类里面的根据request对象，获取头信息，返回用户id
        String id = JwtUtils.getMemberIdByJwtToken(request);
        //查询数据库，根据用户id，获取用户信息
        UcenterMember member = memberService.getById(id);
        return R.ok().data("memberInfo",member);
    }

    @GetMapping("countregister/{day}")
    public R registerCount(@PathVariable String day){
        Integer count = memberService.countRegisterByDay(day);
        return R.ok().data("countRegister",count);
    }

}

