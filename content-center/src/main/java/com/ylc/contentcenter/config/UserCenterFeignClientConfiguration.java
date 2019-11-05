package com.ylc.contentcenter.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;

/**
 * @Description: 用java代码的方式配置feign的日志打印  注解和配置文件中也需要加入
 * 这边不需要加@Configuration注解，如果加上会发生父子上下文的问题
 * @Date: 2019/10/3
 */
public class UserCenterFeignClientConfiguration {

    @Bean
    public Logger.Level level(){
        //让feign打印所有请求的细节
        return Logger.Level.FULL;
    }

}
