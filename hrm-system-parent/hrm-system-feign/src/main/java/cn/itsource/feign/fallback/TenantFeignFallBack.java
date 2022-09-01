package cn.itsource.feign.fallback;

import cn.itsource.feign.client.TenantFeign;
import cn.itsource.result.JSONResult;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @auth: wujiangbo
 * @date: 2022-02-18 18:15
 */
@Component
public class TenantFeignFallBack implements FallbackFactory<TenantFeign> {

    @Override
    public TenantFeign create(Throwable throwable) {
        return new TenantFeign(){

            @Override
            public JSONResult getTenantByLoginId(Long id) {
                throwable.printStackTrace();
                return JSONResult.error("根据LoginId获取机构信息失败-触发兜底函数");
            }
        };
    }
}
