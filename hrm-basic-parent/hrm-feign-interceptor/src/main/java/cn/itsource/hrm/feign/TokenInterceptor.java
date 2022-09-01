package cn.itsource.hrm.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;

//Feign拦截器
@Component
public class TokenInterceptor implements RequestInterceptor {

    //请求头中的token名称
    private final String AUTHORIZATION_HEADER = "Authorization";

    @Override
    public void apply(RequestTemplate template) {
        //1、获取request请求对象，使用RequestContextHolder获取
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        ///2.从请求头获取到令牌
        String token = request.getHeader(AUTHORIZATION_HEADER);

        //3.把令牌添加到 Feign 的请求头
        if(StringUtils.hasLength(token)){
            template.header(AUTHORIZATION_HEADER, token);
        }else{
            // 创建 ClientCredentialsResourceDetails 对象
            ClientCredentialsResourceDetails resourceDetails = new ClientCredentialsResourceDetails();
            resourceDetails.setAccessTokenUri("http://localhost:1050/oauth/token");
            resourceDetails.setClientId("admin");
            resourceDetails.setClientSecret("123456");
            // 创建 OAuth2RestTemplate 对象
            OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(resourceDetails);
            restTemplate.setAccessTokenProvider(new ClientCredentialsAccessTokenProvider());
            template.header(AUTHORIZATION_HEADER, restTemplate.getAccessToken().getValue());
        }
    }
}
