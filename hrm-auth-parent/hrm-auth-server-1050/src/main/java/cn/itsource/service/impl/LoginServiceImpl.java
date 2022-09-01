package cn.itsource.service.impl;

import cn.itsource.domain.Login;
import cn.itsource.dto.AccessTokenDto;
import cn.itsource.dto.LoginMealDto;
import cn.itsource.exception.MyException;
import cn.itsource.mapper.LoginMapper;
import cn.itsource.result.JSONResult;
import cn.itsource.service.ILoginService;
import cn.itsource.utils.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @desc desc 登录表
 * @author tangrufei
 * @date 2022/8/31 13:10
 * @param null
 * @return null
 */
@Service
public class LoginServiceImpl extends ServiceImpl<LoginMapper, Login> implements ILoginService {

    @Autowired
    private LoginMapper loginMapper;

    @Override
    public JSONResult saveLoginMeal(LoginMealDto loginMealDto) {
        loginMapper.saveLoginMeal(loginMealDto);
        return JSONResult.success();
    }

    /**
     * 登陆业务
     * 方法目的：返回token给前端
     */
    @Override
    public JSONResult user(Login login) {
        /**
         * 1、校验入参
         * 2、通过http工具类向认证服务器发请求获取token
         * 3、token封装返回前端
         */
        //http://localhost:1050/oauth/token?client_id=admin&client_secret=123456&grant_type=password&username=zs&password=123456
        if(!StringUtils.hasLength(login.getUsername())){
            throw new MyException("登录账号不能为空");
        }

        String url = "http://localhost:1050/oauth/token?client_id=admin&client_secret=123456&grant_type=password&username=%s&password=%s";

        //设置值
        url = String.format(url, login.getUsername(), login.getPassword());

        //发请求
        String result = HttpUtil.sendPost(url, null);

        //封装
        AccessTokenDto accessTokenDto = JSONObject.parseObject(result, AccessTokenDto.class);
        return JSONResult.success(accessTokenDto);
    }
}
