package cn.itsource.controller;

import cn.itsource.service.ICourseMarketService;
import cn.itsource.domain.CourseMarket;
import cn.itsource.query.CourseMarketQuery;
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
@RequestMapping("/courseMarket")
public class CourseMarketController {

    @Autowired
    public ICourseMarketService courseMarketService;

    /**
     * 保存和修改操作公用此方法
     * @param courseMarket 前端传递来的实体数据
     */
    @PostMapping(value="/save")
    public JSONResult save(@RequestBody CourseMarket courseMarket){
        if(courseMarket.getId()!=null){
                courseMarketService.updateById(courseMarket);
        }else{
                courseMarketService.insert(courseMarket);
        }
        return JSONResult.success();
    }

    /**
    * 根据ID删除指定对象信息
    * @param id
    */
    @DeleteMapping(value="/{id}")
    public JSONResult delete(@PathVariable("id") Long id){
            courseMarketService.deleteById(id);
        return JSONResult.success();
    }

    //根据ID查询对象详情信息
    @GetMapping(value = "/{id}")
    public JSONResult get(@PathVariable("id")Long id)
    {
        return JSONResult.success(courseMarketService.selectById(id));
    }


    /**
    * 查看所有对象数据（不分页）
    */
    @GetMapping(value = "/list")
    public JSONResult list(){
        return JSONResult.success(courseMarketService.selectList(null));
    }


    /**
    * 分页查询数据
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping(value = "/pagelist")
    public JSONResult pageList(@RequestBody CourseMarketQuery query)
    {
        Page<CourseMarket> page = new Page<CourseMarket>(query.getPage(),query.getRows());
        page = courseMarketService.selectPage(page);
        return JSONResult.success(new PageList<CourseMarket>(page.getTotal(), page.getRecords()));
    }
}