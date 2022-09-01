package cn.itsource.feign.client;

import cn.itsource.feign.fallback.TenantFeignFallBack;
import cn.itsource.result.JSONResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-system", fallbackFactory = TenantFeignFallBack.class)
public interface TenantFeign {

    //根据登录人ID获取所属机构信息
    @GetMapping(value = "/tenant/getTenantByLoginId/{id}")
    JSONResult getTenantByLoginId(@PathVariable("id") Long id);
}
