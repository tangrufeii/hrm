package cn.itsource.controller;

import cn.itsource.constants.ErrorCode;
import cn.itsource.service.IEmployeeService;
import cn.itsource.domain.Employee;
import cn.itsource.query.EmployeeQuery;
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
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    public IEmployeeService employeeService;

    /**
     * 保存和修改操作公用此方法
     * @param employee 前端传递来的实体数据
     */
    @PostMapping(value="/save")
    public JSONResult save(@RequestBody Employee employee){
        if(employee.getId()!=null){
                employeeService.updateById(employee);
        }else{
                employeeService.insert(employee);
        }
        return JSONResult.success();
    }

    /**
    * 根据ID删除指定对象信息
    * @param id
    */
    @DeleteMapping(value="/{id}")
    public JSONResult delete(@PathVariable("id") Long id){
            employeeService.deleteById(id);
        return JSONResult.success();
    }

    //根据ID查询对象详情信息
    @GetMapping(value = "/{id}")
    public JSONResult get(@PathVariable("id")Long id)
    {
        return JSONResult.success(employeeService.selectById(id));
    }


    /**
    * 查看所有对象数据（不分页）
    */
    @GetMapping(value = "/list")
    public JSONResult list(){
        return JSONResult.success(employeeService.selectList(null));
    }


    /**
    * 分页查询数据
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping(value = "/pagelist")
    public JSONResult pageList(@RequestBody EmployeeQuery query)
    {
        Page<Employee> page = new Page<Employee>(query.getPage(),query.getRows());
        page = employeeService.selectPage(page);
        return JSONResult.success(new PageList<Employee>(page.getTotal(), page.getRecords()));
    }
}