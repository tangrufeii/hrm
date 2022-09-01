package cn.itsource.config.mp;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @ClassName: MybatisPlusConfig
 * @description: MybatisPlus配置类
 * @author: tangrufei
 * @create: 2021-08-25 12:53:37
 * @Version 1.1.0.1
 */
@Configuration
@MapperScan("cn.itsource.mapper") //mapper接口扫描
@EnableTransactionManagement  //事务管理
public class MybatisPlusConfig {

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

}