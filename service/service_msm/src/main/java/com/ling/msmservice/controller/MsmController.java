package com.ling.msmservice.controller;

import com.ling.commonutils.R;
import com.ling.commonutils.RandomUtil;
import com.ling.msmservice.service.MsmService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/edumsm/msm")
@CrossOrigin
public class MsmController {

    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    //阿里云发送短信的方法
    @GetMapping("/send/{phone}")
    public R sendMsm(@PathVariable String phone){
        //从redis获取验证码，如果能获取，直接返回
        String code = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(code)){
            return R.ok();
        }

        //获取不到就阿里云发送
        //生成随机值，并传递给阿里云短信，让他转发给手机
        code = RandomUtil.getFourBitRandom();
        HashMap<String,Object> param = new HashMap<String, Object>();
        param.put("code",code);
        boolean isSend = msmService.send(param,phone);

        if (isSend){
            //如果发送成功，把发送成功的code验证码保存到redis中，并设置有效时间，设置5分钟过期
            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
            return R.ok();
        }else {
            return R.error().message("短信发送失败");
        }

    }


}
