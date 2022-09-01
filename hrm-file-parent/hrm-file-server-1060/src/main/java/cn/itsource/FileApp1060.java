package cn.itsource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @description:
 * @auth: tangrufei
 * @date: 2022-08-20 10:39
 */
@SpringBootApplication
@EnableEurekaClient
public class FileApp1060 {

    public static void main(String[] args) {
        SpringApplication.run(FileApp1060.class);
    }
}