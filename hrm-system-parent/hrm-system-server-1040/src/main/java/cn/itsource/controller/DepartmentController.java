package cn.itsource.controller;

import cn.itsource.service.IDepartmentService;
import cn.itsource.domain.Department;
import cn.itsource.query.DepartmentQuery;
import cn.itsource.result.JSONResult;
import cn.itsource.result.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @desc
 * @author bobo
 * @since 2022-01-16
 */
@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    public IDepartmentService departmentService;

    /**
     * 保存和修改操作公用此方法
     * @param department 前端传递来的实体数据
     */
    @PostMapping(value="/save")
    public JSONResult save(@RequestBody Department department){
        if(department.getId()!=null){
                departmentService.updateById(department);
        }else{
                departmentService.insert(department);
        }
        return JSONResult.success();
    }

    /**
    * 根据ID删除指定对象信息
    * @param id
    */
    @DeleteMapping(value="/{id}")
    public JSONResult delete(@PathVariable("id") Long id){
            departmentService.deleteById(id);
        return JSONResult.success();
    }

    //根据ID查询对象详情信息
    @GetMapping(value = "/{id}")
    public JSONResult get(@PathVariable("id")Long id)
    {
        return JSONResult.success(departmentService.selectById(id));
    }


    /**
    * 查看所有对象数据（不分页）
    */
    @GetMapping(value = "/list")
    public JSONResult list(){
        return JSONResult.success(departmentService.selectList(null));
    }


    /**
    * 分页查询数据
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping(value = "/pagelist")
    public JSONResult pageList(@RequestBody DepartmentQuery query)
    {
        Page<Department> page = new Page<Department>(query.getPage(),query.getRows());
        page = departmentService.selectPage(page);
        return JSONResult.success(new PageList<Department>(page.getTotal(), page.getRecords()));
    }
}