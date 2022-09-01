package cn.itsource.feign.client;

import cn.itsource.doc.CourseDoc;
import cn.itsource.feign.fallback.EsFeignClientFallback;
import cn.itsource.result.JSONResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

@FeignClient(value = "service-es", fallbackFactory = EsFeignClientFallback.class)
public interface EsFeignClient {

    /**
     * 往ES中存数据
     */
    @PostMapping("/es/saveCourse")
    JSONResult insert(@RequestBody CourseDoc doc);

    /**
     * 往ES中存数据(批量)
     */
    @PostMapping("/es/saveCourseBatch")
    JSONResult insertBatch(@RequestBody List<CourseDoc> list);

    @PostMapping("/es/deleteCourseBatch")
    JSONResult deleteBatch(@RequestBody List<CourseDoc> list);
}