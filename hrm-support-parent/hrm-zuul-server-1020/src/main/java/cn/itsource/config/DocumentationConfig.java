package cn.itsource.config;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

@Component
@Primary
public class DocumentationConfig implements SwaggerResourcesProvider {

    @Override
    public List<SwaggerResource> get() {
        List resources = new ArrayList<>();
        //注意：/hrm/system在yml配置文件已经配置了
        resources.add(swaggerResource("系统管理", "/hrm/system/v2/api-docs", "2.0"));
        resources.add(swaggerResource("课程管理", "/hrm/course/v2/api-docs", "2.0"));//测试一下
        resources.add(swaggerResource("用户管理", "/hrm/user/v2/api-docs", "2.0"));//测试一下
        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location, String version) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }
}
