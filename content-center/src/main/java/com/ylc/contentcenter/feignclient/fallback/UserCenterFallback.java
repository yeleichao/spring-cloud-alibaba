package com.ylc.contentcenter.feignclient.fallback;

import com.ylc.contentcenter.domain.dto.user.UserDTO;
import com.ylc.contentcenter.feignclient.UserCenterFeignClient;
import org.springframework.stereotype.Component;

/**
 * @Description: 请求出错返回得fallback
 * @Date: 2019/10/4
 */
@Component
public class UserCenterFallback implements UserCenterFeignClient {
    @Override
    public UserDTO findById(Integer id) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(999);
        userDTO.setWxNickname("fallback");
        return userDTO;
    }
}
