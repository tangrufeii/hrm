package cn.itsource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @description:
 * @auth: wujiangbo
 * @date: 2022-01-16 11:20
 */
@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
public class ZuulApp1020 {

    public static void main(String[] args) {
        SpringApplication.run(ZuulApp1020.class);
    }
}
