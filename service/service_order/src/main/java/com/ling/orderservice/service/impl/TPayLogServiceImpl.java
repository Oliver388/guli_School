package com.ling.orderservice.service.impl;

import com.ling.orderservice.entity.TPayLog;
import com.ling.orderservice.mapper.TPayLogMapper;
import com.ling.orderservice.service.TPayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author Oliver
 * @since 2022-08-05
 */
@Service
public class TPayLogServiceImpl extends ServiceImpl<TPayLogMapper, TPayLog> implements TPayLogService {

}
