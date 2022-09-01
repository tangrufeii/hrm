package cn.itsource.controller;

import cn.itsource.domain.CourseType;
import cn.itsource.query.CourseTypeQuery;
import cn.itsource.result.JSONResult;
import cn.itsource.result.PageList;
import cn.itsource.service.ICourseTypeService;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * @desc
 * @author tangrufei
 * @since 2022-08-20
 */
@RestController
@RequestMapping("/courseType")
public class CourseTypeController {

    @Autowired
    public ICourseTypeService courseTypeService;

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/crumbs/{courseTypeId}")
    public JSONResult crumbs(@PathVariable("courseTypeId") Long courseTypeId){
        return courseTypeService.crumbs(courseTypeId);
    }

    @GetMapping("/treeData")
    public JSONResult treeData(){
        return courseTypeService.treeData();
    }

    /**
     * 保存和修改操作公用此方法
     * @param courseType 前端传递来的实体数据
     */
    @PostMapping(value="/save")
    @CacheEvict(cacheNames = "courseType", key = "'courseTypeTree'")
    public JSONResult save(@RequestBody CourseType courseType){
        if(courseType.getId()!=null){
                courseTypeService.updateById(courseType);
        }else{
                courseTypeService.insert(courseType);
        }
        return JSONResult.success();
    }

    /**
    * 根据ID删除指定对象信息
    * @param id
    */
    @DeleteMapping(value="/{id}")
    @CacheEvict(cacheNames = "courseType", key = "'courseTypeTree'")
    public JSONResult delete(@PathVariable("id") Long id){
            courseTypeService.deleteById(id);
        return JSONResult.success();
    }

    //根据ID查询对象详情信息
    @GetMapping(value = "/{id}")
    public JSONResult get(@PathVariable("id")Long id)
    {
        return JSONResult.success(courseTypeService.selectById(id));
    }


    /**
    * 查看所有对象数据（不分页）
    */
    @GetMapping(value = "/list")
    public JSONResult list(){
        return JSONResult.success(courseTypeService.selectList(null));
    }


    /**
    * 分页查询数据
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping(value = "/pagelist")
    public JSONResult pageList(@RequestBody CourseTypeQuery query)
    {
        Page<CourseType> page = new Page<CourseType>(query.getPage(),query.getRows());
        page = courseTypeService.selectPage(page);
        return JSONResult.success(new PageList<CourseType>(page.getTotal(), page.getRecords()));
    }
}
