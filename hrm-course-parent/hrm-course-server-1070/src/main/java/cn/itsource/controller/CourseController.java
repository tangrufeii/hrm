package cn.itsource.controller;

import cn.itsource.dto.CourseAddDto;
import cn.itsource.query.CourseQuery;
import cn.itsource.result.JSONResult;
import cn.itsource.result.PageList;
import cn.itsource.service.ICourseService;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.*;

/**
 * @desc
 * @author tangrufei
 * @since 2022-08-20
 */
@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    public ICourseService courseService;

    /**
     * 客户端模式直接获取token
     */
    @GetMapping(value="/getToken")
    public OAuth2AccessToken getToken(){
        // 创建 ClientCredentialsResourceDetails 对象
        ClientCredentialsResourceDetails resourceDetails = new ClientCredentialsResourceDetails();
        resourceDetails.setAccessTokenUri("http://localhost:1050/oauth/token");
        resourceDetails.setClientId("admin");
        resourceDetails.setClientSecret("123456");
        // 创建 OAuth2RestTemplate 对象
        OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(resourceDetails);
        restTemplate.setAccessTokenProvider(new ClientCredentialsAccessTokenProvider());
        // 获取访问令牌
        return restTemplate.getAccessToken();
    }

    /**
     * 课程批量下线
     */
    @PostMapping("/offLineCourse")
    public JSONResult offLineCourse(@RequestBody CourseQuery query){
        return courseService.offLineCourse(query);
    }

    /**
     * 课程批量上线
     */
    @PostMapping("/onLineCourse")
    @PreAuthorize("hasAuthority('course:online')")
    public JSONResult onLineCourse(@RequestBody CourseQuery query){
        return courseService.onLineCourse(query);
    }

    /**
     * 保存
     */
    @PostMapping(value="/save")
    public JSONResult save(@RequestBody CourseAddDto dto){
        return courseService.save(dto);
    }

    /**
     * 修改
     */
    @PostMapping(value="/update")
    public JSONResult update(@RequestBody CourseAddDto dto){
        return courseService.updateMyData(dto);
    }

    /**
    * 根据ID删除指定对象信息
    * @param id
    */
    @DeleteMapping(value="/{id}")
    public JSONResult delete(@PathVariable("id") Long id){
            courseService.deleteById(id);
        return JSONResult.success();
    }

    //根据ID查询对象详情信息
    @GetMapping(value = "/{id}")
    public JSONResult get(@PathVariable("id")Long id)
    {
        return JSONResult.success(courseService.selectById(id));
    }


    /**
    * 查看所有对象数据（不分页）
    */
    @GetMapping(value = "/list")
    @PreAuthorize("hasAuthority('course:list')")
    public JSONResult list(){
        return JSONResult.success(courseService.selectList(null));
    }


    /**
    * 分页查询数据
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping(value = "/pagelist")
    @PreAuthorize("hasAuthority('course:pagelist')")
    public JSONResult pageList(@RequestBody CourseQuery query)
    {
        Page<CourseAddDto> page = courseService.selectMyPage(query);
        return JSONResult.success(new PageList<CourseAddDto>(page.getTotal(), page.getRecords()));
    }
}
