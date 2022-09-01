package cn.itsource.config.oauth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

//资源服务配置
@Configuration
//开启资源服务配置
@EnableResourceServer
//开启方法授权
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MyResourceServerConfig extends ResourceServerConfigurerAdapter {

    //1.资源服务安全配置

    //JWT令牌
    @Bean
    public TokenStore tokenStore(){
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    //设置JWT签名密钥。它可以是简单的MAC密钥，也可以是RSA密钥
    private final String sign_key  = "itsource";

    //JWT令牌校验工具
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        //设置JWT签名密钥。它可以是简单的MAC密钥，也可以是RSA密钥
        jwtAccessTokenConverter.setSigningKey(sign_key);
        return jwtAccessTokenConverter;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        //资源ID
        resources.resourceId("systemId").tokenStore(tokenStore());
    }

    //2.资源服务的资源的授权配置，比如那些资源放行，那些资源需要什么权限等等
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //校验scope必须为all ， 对应认证服务的客户端详情配置的clientId
                .antMatchers("/tenantType/list", "/tenant/register").permitAll()
                .antMatchers("/**").access("#oauth2.hasScope('hrm')")
                //关闭跨域伪造检查
                .and().csrf().disable()
                //把session设置为无状态，意思是使用了token，那么session不再做数据的记录
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
