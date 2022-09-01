package cn.itsource.tools;

import cn.itsource.domain.Login;
import com.alibaba.fastjson.JSON;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;


public class AuthTools {

    //获取用户登录信息
    public static Login getLoginInfo(){
        SecurityContext context = SecurityContextHolder.getContext();
        if(context == null){
            return null;
        }
        Authentication authentication = context.getAuthentication();
        if(authentication == null){
            return null;
        }
        return JSON.parseObject(authentication.getPrincipal().toString(), Login.class);
    }
}
