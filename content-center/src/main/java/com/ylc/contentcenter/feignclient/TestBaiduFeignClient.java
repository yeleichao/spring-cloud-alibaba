package com.ylc.contentcenter.feignclient;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Description: feign 调用不再nacos注册中心上的地址 案例demo
 * @Date: 2019/10/3
 */
@FeignClient(name = "xxx", url = "http://www.baidu.com")
public interface TestBaiduFeignClient {
}
