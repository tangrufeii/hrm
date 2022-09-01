package cn.itsource.controller;

import cn.itsource.service.ICourseCollectService;
import cn.itsource.domain.CourseCollect;
import cn.itsource.query.CourseCollectQuery;
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
@RequestMapping("/courseCollect")
public class CourseCollectController {

    @Autowired
    public ICourseCollectService courseCollectService;

    /**
     * 保存和修改操作公用此方法
     * @param courseCollect 前端传递来的实体数据
     */
    @PostMapping(value="/save")
    public JSONResult save(@RequestBody CourseCollect courseCollect){
        if(courseCollect.getId()!=null){
                courseCollectService.updateById(courseCollect);
        }else{
                courseCollectService.insert(courseCollect);
        }
        return JSONResult.success();
    }

    /**
    * 根据ID删除指定对象信息
    * @param id
    */
    @DeleteMapping(value="/{id}")
    public JSONResult delete(@PathVariable("id") Long id){
            courseCollectService.deleteById(id);
        return JSONResult.success();
    }

    //根据ID查询对象详情信息
    @GetMapping(value = "/{id}")
    public JSONResult get(@PathVariable("id")Long id)
    {
        return JSONResult.success(courseCollectService.selectById(id));
    }


    /**
    * 查看所有对象数据（不分页）
    */
    @GetMapping(value = "/list")
    public JSONResult list(){
        return JSONResult.success(courseCollectService.selectList(null));
    }


    /**
    * 分页查询数据
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping(value = "/pagelist")
    public JSONResult pageList(@RequestBody CourseCollectQuery query)
    {
        Page<CourseCollect> page = new Page<CourseCollect>(query.getPage(),query.getRows());
        page = courseCollectService.selectPage(page);
        return JSONResult.success(new PageList<CourseCollect>(page.getTotal(), page.getRecords()));
    }
}