package com.ylc.contentcenter.config;

import com.alibaba.cloud.sentinel.annotation.SentinelRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @Description: TODO
 * @Date: 2019/9/15
 */
@Configuration
public class RestTemplateConfig {

    @Bean("restTemplate")
    @LoadBalanced  //ribbon负载均衡
    @SentinelRestTemplate //sentinel与RestTemplate整合
    public RestTemplate getRestTemplate(){
        return new RestTemplateBuilder().build();
    }
}
