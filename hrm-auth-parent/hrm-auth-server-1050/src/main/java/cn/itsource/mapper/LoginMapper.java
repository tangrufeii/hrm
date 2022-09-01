package cn.itsource.mapper;

import cn.itsource.domain.Login;
import cn.itsource.dto.LoginMealDto;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * @desc desc 登录表接口
 * @author tangrufei
 * @date 2022/8/31 13:08
 * @return null
 */
public interface LoginMapper extends BaseMapper<Login> {

    void saveLoginMeal(LoginMealDto loginMealDto);

    Login selectByUsername(String username);
}
