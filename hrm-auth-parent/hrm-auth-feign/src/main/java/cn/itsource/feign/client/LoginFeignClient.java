package cn.itsource.feign.client;

import cn.itsource.domain.Login;
import cn.itsource.dto.LoginMealDto;
import cn.itsource.result.JSONResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("service-auth")
public interface LoginFeignClient {

    @PostMapping(value="/login/save")
    JSONResult save(@RequestBody Login login);

    @PostMapping(value="/login/saveLoginMeal")
    JSONResult saveLoginMeal(@RequestBody LoginMealDto loginMealDto);
}