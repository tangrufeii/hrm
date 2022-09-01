package cn.itsource.controller;

import cn.itsource.service.ICourseDetailService;
import cn.itsource.domain.CourseDetail;
import cn.itsource.query.CourseDetailQuery;
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
@RequestMapping("/courseDetail")
public class CourseDetailController {

    @Autowired
    public ICourseDetailService courseDetailService;

    /**
     * 保存和修改操作公用此方法
     * @param courseDetail 前端传递来的实体数据
     */
    @PostMapping(value="/save")
    public JSONResult save(@RequestBody CourseDetail courseDetail){
        if(courseDetail.getId()!=null){
                courseDetailService.updateById(courseDetail);
        }else{
                courseDetailService.insert(courseDetail);
        }
        return JSONResult.success();
    }

    /**
    * 根据ID删除指定对象信息
    * @param id
    */
    @DeleteMapping(value="/{id}")
    public JSONResult delete(@PathVariable("id") Long id){
            courseDetailService.deleteById(id);
        return JSONResult.success();
    }

    //根据ID查询对象详情信息
    @GetMapping(value = "/{id}")
    public JSONResult get(@PathVariable("id")Long id)
    {
        return JSONResult.success(courseDetailService.selectById(id));
    }


    /**
    * 查看所有对象数据（不分页）
    */
    @GetMapping(value = "/list")
    public JSONResult list(){
        return JSONResult.success(courseDetailService.selectList(null));
    }


    /**
    * 分页查询数据
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping(value = "/pagelist")
    public JSONResult pageList(@RequestBody CourseDetailQuery query)
    {
        Page<CourseDetail> page = new Page<CourseDetail>(query.getPage(),query.getRows());
        page = courseDetailService.selectPage(page);
        return JSONResult.success(new PageList<CourseDetail>(page.getTotal(), page.getRecords()));
    }
}