package cn.itsource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @description:
 * @auth: tangrufei
 * @date: 2022-08-20
 */
@SpringBootApplication
@EnableEurekaClient
public class UserApp1100 {

    public static void main(String[] args) {
        SpringApplication.run(UserApp1100.class);
    }
}
