package com.ling.orderservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ling.commonutils.JwtUtils;
import com.ling.commonutils.R;
import com.ling.orderservice.entity.TOrder;
import com.ling.orderservice.service.TOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.filter.OrderedFilter;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author Oliver
 * @since 2022-08-05
 */
@RestController
@RequestMapping("/eduorder/order")
@CrossOrigin
public class TOrderController {

    @Autowired
    private TOrderService orderService;

    //根据课程id和用户id创建订单，返回订单id
    @PostMapping("createOrder/{courseId}")
    public R save(@PathVariable String courseId, HttpServletRequest request){
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        String orderId = orderService.saveOrder(courseId, memberId);
        return R.ok().data("orderId",orderId);
    }

    @GetMapping("getOrder/{orderId}")
    public R get(@PathVariable String orderId){
        LambdaQueryWrapper<TOrder> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(TOrder::getOrderNo,orderId);
        TOrder order = orderService.getOne(wrapper);
        return R.ok().data("item",order);

    }
}

