package cn.itsource.controller;

import cn.itsource.service.IOperationLogService;
import cn.itsource.domain.OperationLog;
import cn.itsource.query.OperationLogQuery;
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
@RequestMapping("/operationLog")
public class OperationLogController {

    @Autowired
    public IOperationLogService operationLogService;

    /**
     * 保存和修改操作公用此方法
     * @param operationLog 前端传递来的实体数据
     */
    @PostMapping(value="/save")
    public JSONResult save(@RequestBody OperationLog operationLog){
        if(operationLog.getId()!=null){
                operationLogService.updateById(operationLog);
        }else{
                operationLogService.insert(operationLog);
        }
        return JSONResult.success();
    }

    /**
    * 根据ID删除指定对象信息
    * @param id
    */
    @DeleteMapping(value="/{id}")
    public JSONResult delete(@PathVariable("id") Long id){
            operationLogService.deleteById(id);
        return JSONResult.success();
    }

    //根据ID查询对象详情信息
    @GetMapping(value = "/{id}")
    public JSONResult get(@PathVariable("id")Long id)
    {
        return JSONResult.success(operationLogService.selectById(id));
    }


    /**
    * 查看所有对象数据（不分页）
    */
    @GetMapping(value = "/list")
    public JSONResult list(){
        return JSONResult.success(operationLogService.selectList(null));
    }


    /**
    * 分页查询数据
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping(value = "/pagelist")
    public JSONResult pageList(@RequestBody OperationLogQuery query)
    {
        Page<OperationLog> page = new Page<OperationLog>(query.getPage(),query.getRows());
        page = operationLogService.selectPage(page);
        return JSONResult.success(new PageList<OperationLog>(page.getTotal(), page.getRecords()));
    }
}