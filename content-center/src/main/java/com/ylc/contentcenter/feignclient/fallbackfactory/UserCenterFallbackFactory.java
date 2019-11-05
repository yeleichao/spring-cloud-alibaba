package com.ylc.contentcenter.feignclient.fallbackfactory;

import com.ylc.contentcenter.domain.dto.user.UserDTO;
import com.ylc.contentcenter.feignclient.UserCenterFeignClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Description: fallbackFactory 可以得到异常信息
 * @Date: 2019/10/4
 */
@Slf4j
@Component
public class UserCenterFallbackFactory implements FallbackFactory<UserCenterFeignClient>{


    @Override
    public UserCenterFeignClient create(Throwable cause) {
        return new UserCenterFeignClient() {
            @Override
            public UserDTO findById(Integer id) {
                log.warn("远程调用被限流或降级",cause);
                UserDTO userDTO = new UserDTO();
                userDTO.setId(999);
                userDTO.setWxNickname("fallback");
                return userDTO;
            }
        };

    }
}
