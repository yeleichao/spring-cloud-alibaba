package com.ylc.contentcenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

//通用mapper,扫描接口
@MapperScan(basePackages = "com.ylc")
@ComponentScan(basePackages = {"com.ylc"} )
@EnableFeignClients
@EnableBinding(Source.class)
@SpringBootApplication
public class ContentCenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContentCenterApplication.class, args);
	}

}
