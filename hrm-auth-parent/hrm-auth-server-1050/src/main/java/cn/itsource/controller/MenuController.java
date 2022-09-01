package cn.itsource.controller;

import cn.itsource.service.IMenuService;
import cn.itsource.domain.Menu;
import cn.itsource.query.MenuQuery;
import cn.itsource.result.JSONResult;
import cn.itsource.result.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    public IMenuService menuService;

    /**
     * 保存和修改操作公用此方法
     * @param menu 前端传递来的实体数据
     */
    @PostMapping(value="/save")
    public JSONResult save(@RequestBody Menu menu){
        if(menu.getId()!=null){
                menuService.updateById(menu);
        }else{
                menuService.insert(menu);
        }
        return JSONResult.success();
    }

    /**
    * 根据ID删除指定对象信息
    * @param id
    */
    @DeleteMapping(value="/{id}")
    public JSONResult delete(@PathVariable("id") Long id){
            menuService.deleteById(id);
        return JSONResult.success();
    }

    //根据ID查询对象详情信息
    @GetMapping(value = "/{id}")
    public JSONResult get(@PathVariable("id")Long id)
    {
        return JSONResult.success(menuService.selectById(id));
    }


    /**
    * 查看所有对象数据（不分页）
    */
    @GetMapping(value = "/list")
    public JSONResult list(){
        return JSONResult.success(menuService.selectList(null));
    }


    /**
    * 分页查询数据
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping(value = "/pagelist")
    public JSONResult pageList(@RequestBody MenuQuery query)
    {
        Page<Menu> page = new Page<Menu>(query.getPage(),query.getRows());
        page = menuService.selectPage(page);
        return JSONResult.success(new PageList<Menu>(page.getTotal(), page.getRecords()));
    }
}