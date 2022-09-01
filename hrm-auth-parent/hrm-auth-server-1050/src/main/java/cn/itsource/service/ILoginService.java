package cn.itsource.service;

import cn.itsource.domain.Login;
import cn.itsource.dto.LoginMealDto;
import cn.itsource.result.JSONResult;
import com.baomidou.mybatisplus.service.IService;

/**
 * @desc desc 登录表
 * @author tangrufei
 * @date 2022/8/31 13:18
 * @param null
 * @return null
 */
public interface ILoginService extends IService<Login> {

    JSONResult saveLoginMeal(LoginMealDto loginMealDto);

    JSONResult user(Login login);
}
