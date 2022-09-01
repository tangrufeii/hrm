package cn.itsource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication(exclude = DataSourceAutoConfiguration.class) //排除SpringBoot默认的数据源
@EnableEurekaClient
@EnableFeignClients("cn.itsource.feign.client")
public class AuthApp1050 {

    public static void main(String[] args) {
        SpringApplication.run(AuthApp1050.class);
    }
}
