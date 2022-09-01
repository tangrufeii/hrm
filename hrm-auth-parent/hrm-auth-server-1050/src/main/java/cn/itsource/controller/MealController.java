package cn.itsource.controller;

import cn.itsource.service.IMealService;
import cn.itsource.domain.Meal;
import cn.itsource.query.MealQuery;
import cn.itsource.result.JSONResult;
import cn.itsource.result.PageList;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/meal")
public class MealController {

    @Autowired
    public IMealService mealService;

    /**
     * 保存和修改操作公用此方法
     * @param meal 前端传递来的实体数据
     */
    @PostMapping(value="/save")
    public JSONResult save(@RequestBody Meal meal){
        if(meal.getId()!=null){
                mealService.updateById(meal);
        }else{
                mealService.insert(meal);
        }
        return JSONResult.success();
    }

    /**
    * 根据ID删除指定对象信息
    * @param id
    */
    @DeleteMapping(value="/{id}")
    public JSONResult delete(@PathVariable("id") Long id){
            mealService.deleteById(id);
        return JSONResult.success();
    }

    //根据ID查询对象详情信息
    @GetMapping(value = "/{id}")
    public JSONResult get(@PathVariable("id")Long id)
    {
        return JSONResult.success(mealService.selectById(id));
    }


    /**
    * 查看所有对象数据（不分页）
    */
    @GetMapping(value = "/list")
    public JSONResult list(){
        Wrapper wrapper = new EntityWrapper<Meal>();
        wrapper.eq("status", 0);
        List<Meal> meals = mealService.selectList(wrapper);
        return JSONResult.success(meals);
    }


    /**
    * 分页查询数据
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping(value = "/pagelist")
    public JSONResult pageList(@RequestBody MealQuery query)
    {
        Page<Meal> page = new Page<Meal>(query.getPage(),query.getRows());
        page = mealService.selectPage(page);
        return JSONResult.success(new PageList<Meal>(page.getTotal(), page.getRecords()));
    }
}