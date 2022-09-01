package cn.itsource.controller;

import cn.itsource.service.IRoleService;
import cn.itsource.domain.Role;
import cn.itsource.query.RoleQuery;
import cn.itsource.result.JSONResult;
import cn.itsource.result.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @desc desc
 * @author tangrufei
 * @date 2022/8/31 13:11
 * @param null
 * @return null
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    public IRoleService roleService;

    /**
     * 保存和修改操作公用此方法
     * @param role 前端传递来的实体数据
     */
    @PostMapping(value="/save")
    public JSONResult save(@RequestBody Role role){
        if(role.getId()!=null){
                roleService.updateById(role);
        }else{
                roleService.insert(role);
        }
        return JSONResult.success();
    }

    /**
    * 根据ID删除指定对象信息
    * @param id
    */
    @DeleteMapping(value="/{id}")
    public JSONResult delete(@PathVariable("id") Long id){
            roleService.deleteById(id);
        return JSONResult.success();
    }

    //根据ID查询对象详情信息
    @GetMapping(value = "/{id}")
    public JSONResult get(@PathVariable("id")Long id)
    {
        return JSONResult.success(roleService.selectById(id));
    }


    /**
    * 查看所有对象数据（不分页）
    */
    @GetMapping(value = "/list")
    public JSONResult list(){
        return JSONResult.success(roleService.selectList(null));
    }


    /**
    * 分页查询数据
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping(value = "/pagelist")
    public JSONResult pageList(@RequestBody RoleQuery query)
    {
        Page<Role> page = new Page<Role>(query.getPage(),query.getRows());
        page = roleService.selectPage(page);
        return JSONResult.success(new PageList<Role>(page.getTotal(), page.getRecords()));
    }
}