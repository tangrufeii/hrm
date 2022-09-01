package cn.itsource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableEurekaClient
@EnableCaching //开启缓存注解
@EnableFeignClients("cn.itsource.feign.client")
public class CourseApp1070 {

    public static void main(String[] args) {
        SpringApplication.run(CourseApp1070.class);
    }
}
