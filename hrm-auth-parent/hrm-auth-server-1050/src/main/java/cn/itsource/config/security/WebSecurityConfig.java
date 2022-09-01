package cn.itsource.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity//开启Security Web安全配置
@EnableGlobalMethodSecurity(prePostEnabled = true)//开启方法授权注解支持
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    //注册一个认证管理器给Spring，这个是oAuth2的密码模式会用到
    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    //密码编码器
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //授权规则配置
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()                                //授权配置
                .antMatchers("/login", "/login/user", "/login/getToken", "/meal/list", "/login/save", "/login/saveLoginMeal").permitAll()  //登录路径放行
                .anyRequest().authenticated()                   //所有请求都需要登录后才能访问
                .and().formLogin()
                .and().logout().permitAll()                    //登出路径放行 /logout
                .and().csrf().disable();                        //关闭跨域伪造检查
    }
}
