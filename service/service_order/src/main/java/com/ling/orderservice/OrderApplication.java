package com.ling.orderservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.stereotype.Component;

/**
 * @author linglifan
 * @date 2022/08/05 10:42
 */
@SpringBootApplication
@EnableFeignClients
@Component("com.ling")
@MapperScan("com.ling.orderservice.mapper")
@EnableDiscoveryClient//服务发现功能
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class);
    }
}
