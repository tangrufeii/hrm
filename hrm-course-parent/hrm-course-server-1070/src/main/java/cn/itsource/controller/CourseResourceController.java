package cn.itsource.controller;

import cn.itsource.service.ICourseResourceService;
import cn.itsource.domain.CourseResource;
import cn.itsource.query.CourseResourceQuery;
import cn.itsource.result.JSONResult;
import cn.itsource.result.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @desc
 * @author tangrufei
 * @since 2022-08-20
 */
@RestController
@RequestMapping("/courseResource")
public class CourseResourceController {

    @Autowired
    public ICourseResourceService courseResourceService;

    /**
     * 保存和修改操作公用此方法
     * @param courseResource 前端传递来的实体数据
     */
    @PostMapping(value="/save")
    public JSONResult save(@RequestBody CourseResource courseResource){
        if(courseResource.getId()!=null){
                courseResourceService.updateById(courseResource);
        }else{
                courseResourceService.insert(courseResource);
        }
        return JSONResult.success();
    }

    /**
    * 根据ID删除指定对象信息
    * @param id
    */
    @DeleteMapping(value="/{id}")
    public JSONResult delete(@PathVariable("id") Long id){
            courseResourceService.deleteById(id);
        return JSONResult.success();
    }

    //根据ID查询对象详情信息
    @GetMapping(value = "/{id}")
    public JSONResult get(@PathVariable("id")Long id)
    {
        return JSONResult.success(courseResourceService.selectById(id));
    }


    /**
    * 查看所有对象数据（不分页）
    */
    @GetMapping(value = "/list")
    public JSONResult list(){
        return JSONResult.success(courseResourceService.selectList(null));
    }


    /**
    * 分页查询数据
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping(value = "/pagelist")
    public JSONResult pageList(@RequestBody CourseResourceQuery query)
    {
        Page<CourseResource> page = new Page<CourseResource>(query.getPage(),query.getRows());
        page = courseResourceService.selectPage(page);
        return JSONResult.success(new PageList<CourseResource>(page.getTotal(), page.getRecords()));
    }
}