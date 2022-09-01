package cn.itsource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @description:
 * @auth: wujiangbo
 * @date: 2022-01-23 09:30
 */
@SpringBootApplication
@EnableEurekaClient
public class EsApp1080 {

    public static void main(String[] args) {
        SpringApplication.run(EsApp1080.class);
    }
}