package com.ling.educenter.service;

import com.ling.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ling.educenter.entity.vo.LoginVo;
import com.ling.educenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-03-31
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(LoginVo loginVo);

    void register(RegisterVo registerVo);

    UcenterMember getMemberByOpenId(String openid);

    Integer countRegisterByDay(String day);
}
