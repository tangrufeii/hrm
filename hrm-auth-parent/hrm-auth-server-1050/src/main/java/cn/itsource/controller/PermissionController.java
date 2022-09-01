package cn.itsource.controller;

import cn.itsource.service.IPermissionService;
import cn.itsource.domain.Permission;
import cn.itsource.query.PermissionQuery;
import cn.itsource.result.JSONResult;
import cn.itsource.result.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @desc
 * @author bobo
 * @since 2022-01-17
 */
@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    public IPermissionService permissionService;

    /**
     * 保存和修改操作公用此方法
     * @param permission 前端传递来的实体数据
     */
    @PostMapping(value="/save")
    public JSONResult save(@RequestBody Permission permission){
        if(permission.getId()!=null){
                permissionService.updateById(permission);
        }else{
                permissionService.insert(permission);
        }
        return JSONResult.success();
    }

    /**
    * 根据ID删除指定对象信息
    * @param id
    */
    @DeleteMapping(value="/{id}")
    public JSONResult delete(@PathVariable("id") Long id){
            permissionService.deleteById(id);
        return JSONResult.success();
    }

    //根据ID查询对象详情信息
    @GetMapping(value = "/{id}")
    public JSONResult get(@PathVariable("id")Long id)
    {
        return JSONResult.success(permissionService.selectById(id));
    }


    /**
    * 查看所有对象数据（不分页）
    */
    @GetMapping(value = "/list")
    public JSONResult list(){
        return JSONResult.success(permissionService.selectList(null));
    }


    /**
    * 分页查询数据
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping(value = "/pagelist")
    public JSONResult pageList(@RequestBody PermissionQuery query)
    {
        Page<Permission> page = new Page<Permission>(query.getPage(),query.getRows());
        page = permissionService.selectPage(page);
        return JSONResult.success(new PageList<Permission>(page.getTotal(), page.getRecords()));
    }
}