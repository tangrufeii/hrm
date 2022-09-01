package cn.itsource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @description:
 * @auth: wujiangbo
 * @date: 2022-01-16 11:15
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaApp1010 {

    public static void main(String[] args) {
        SpringApplication.run(EurekaApp1010.class);
    }
}