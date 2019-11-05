package com.ylc.contentcenter.feignclient;

import com.ylc.contentcenter.config.UserCenterFeignClientConfiguration;
import com.ylc.contentcenter.domain.dto.user.UserDTO;
import com.ylc.contentcenter.feignclient.fallback.UserCenterFallback;
import com.ylc.contentcenter.feignclient.fallbackfactory.UserCenterFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Description: feign client
 * configuration是打印日志用的，还需要将UserCenterFeignClient这个类的日志级别设为debug，如下
 * logging:
 *   level:
 *       com.ylc.contentcenter.feignclient.UserCenterFeignClient: debug
 *  这里如果使用了fallback就不能使用fallbackfactory,推荐使用fallbackfactory 可以得到异常信息
 * @Date: 2019/10/2
 */
//@FeignClient(name = "user-center",configuration = UserCenterFeignClientConfiguration.class)
@FeignClient(name = "user-center", fallbackFactory = UserCenterFallbackFactory.class)
public interface UserCenterFeignClient {

    /**
     * 以下接口会构造出 http:.//user-center/users/{id}
     * 注意，使用feign调用时 PathVariable形式的参数,则要用value=""标明对应的参数,否则会抛出IllegalStateException异常
     */
    @GetMapping("/users/{id}")
    UserDTO findById(@PathVariable(value = "id") Integer id);

}
