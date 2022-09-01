package cn.itsource.feign.fallback;

import cn.itsource.doc.CourseDoc;
import cn.itsource.feign.client.EsFeignClient;
import cn.itsource.result.JSONResult;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description: 兜底类
 * @auth: tangrufei
 * @date: 2022-08-23 11:16
 */
@Component
public class EsFeignClientFallback implements FallbackFactory<EsFeignClient> {

    @Override
    public EsFeignClient create(Throwable throwable) {
        return new EsFeignClient(){
            @Override
            public JSONResult insert(CourseDoc doc) {
                throwable.printStackTrace();
                return JSONResult.error("保存单个课程信息到ES失败");
            }

            @Override
            public JSONResult insertBatch(List<CourseDoc> list) {
                throwable.printStackTrace();
                return JSONResult.error("批量保存课程信息到ES失败");
            }

            @Override
            public JSONResult deleteBatch(List<CourseDoc> list) {
                throwable.printStackTrace();
                return JSONResult.error("批量下架课程信息失败");
            }
        };
    }
}
