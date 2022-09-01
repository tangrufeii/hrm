package cn.itsource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @description:
 * @auth: wujiangbo
 * @date: 2022-01-16 11:25
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class) //排除SpringBoot默认的数据源
@EnableEurekaClient
@EnableFeignClients("cn.itsource.feign.client")
public class SystemApp1040 {

    public static void main(String[] args) {
        SpringApplication.run(SystemApp1040.class);
    }
}
