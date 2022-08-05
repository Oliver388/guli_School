package com.ling.orderservice.service.impl;

import com.ling.commonutils.vo.EduCourseVo;
import com.ling.commonutils.vo.UcenterMemberVo;
import com.ling.orderservice.client.EduClient;
import com.ling.orderservice.client.UcenterClient;
import com.ling.orderservice.entity.TOrder;
import com.ling.orderservice.mapper.TOrderMapper;
import com.ling.orderservice.service.TOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ling.orderservice.uitls.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author Oliver
 * @since 2022-08-05
 */
@Service
public class TOrderServiceImpl extends ServiceImpl<TOrderMapper, TOrder> implements TOrderService {

    @Autowired
    private EduClient eduClient;

    @Autowired
    private UcenterClient ucenterClient;

    //传入课程id和用户id创建课程，再返回课程id
    @Override
    public String saveOrder(String courseId, String memberId) {
        //远程调用课程服务，根据课程id获取课程信息
        EduCourseVo courseVo = eduClient.getCourseInfoDto(courseId);

        //远程调用用户服务，根据用户id获取用户信息
        UcenterMemberVo ucenterMemberVo = ucenterClient.getInfo(memberId);

        //创建订单
        TOrder order = new TOrder();
        order.setOrderNo(OrderNoUtil.getOrderNo());
        order.setCourseId(courseId);
        order.setCourseTitle(courseVo.getTitle());
        order.setCourseCover(courseVo.getCover());
        order.setTeacherName("test");
        order.setTotalFee(courseVo.getPrice());
        order.setMemberId(memberId);
        order.setMobile(ucenterMemberVo.getMobile());
        order.setNickname(ucenterMemberVo.getNickname());
        order.setStatus(0);
        order.setPayType(1);
        baseMapper.insert(order);

        return order.getOrderNo();
    }
}
