package com.ling.eduservice.controller;

import com.ling.commonutils.R;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eduservice/user")
@CrossOrigin
public class EduLoginController {

    //login
    @PostMapping("login")
    public R Login(){
        return R.ok().data("token","admin");
    }

    //info
    @GetMapping("info")
    public R getInfo(){
        return R.ok().data("roles","[admin]").data("name","admin").data("avatar","http://www.weixintouxiang.cn/weixin/20140607090832328.gif");
    }
}

