package com.ylc.usercenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

//通用mapper,扫描接口
@MapperScan(basePackages = "com.ylc")
@ComponentScan(basePackages = {"com.ylc"} )
@SpringBootApplication
//@EnableDiscoveryClient
public class UserCenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserCenterApplication.class, args);
	}

}
