package com.qingzhou.quareat_java;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.client.RestTemplate;

@MapperScan(basePackages = "com.qingzhou.quareat_java.dao")
@EnableAspectJAutoProxy
@SpringBootApplication
public class QuareatJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuareatJavaApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
