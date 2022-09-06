package cn.itsource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @description:
 * @auth: tangrufei
 * @date: 2022-08-20 10:39
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaApp1010 {

    public static void main(String[] args) {
        SpringApplication.run(EurekaApp1010.class);
    }
}