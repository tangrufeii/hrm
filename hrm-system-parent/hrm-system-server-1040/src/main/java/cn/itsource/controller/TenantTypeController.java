package cn.itsource.controller;

import cn.itsource.service.ITenantTypeService;
import cn.itsource.domain.TenantType;
import cn.itsource.query.TenantTypeQuery;
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
@RequestMapping("/tenantType")
public class TenantTypeController {

    @Autowired
    public ITenantTypeService tenantTypeService;

    /**
     * 保存和修改操作公用此方法
     * @param tenantType 前端传递来的实体数据
     */
    @PostMapping(value="/save")
    public JSONResult save(@RequestBody TenantType tenantType){
        if(tenantType.getId()!=null){
                tenantTypeService.updateById(tenantType);
        }else{
                tenantTypeService.insert(tenantType);
        }
        return JSONResult.success();
    }

    /**
    * 根据ID删除指定对象信息
    * @param id
    */
    @DeleteMapping(value="/{id}")
    public JSONResult delete(@PathVariable("id") Long id){
            tenantTypeService.deleteById(id);
        return JSONResult.success();
    }

    //根据ID查询对象详情信息
    @GetMapping(value = "/{id}")
    public JSONResult get(@PathVariable("id")Long id)
    {
        return JSONResult.success(tenantTypeService.selectById(id));
    }


    /**
    * 查看所有对象数据（不分页）
    */
    @GetMapping(value = "/list")
    public JSONResult list(){
        return JSONResult.success(tenantTypeService.selectList(null));
    }


    /**
    * 分页查询数据
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping(value = "/pagelist")
    public JSONResult pageList(@RequestBody TenantTypeQuery query)
    {
        Page<TenantType> page = new Page<TenantType>(query.getPage(),query.getRows());
        page = tenantTypeService.selectPage(page);
        return JSONResult.success(new PageList<TenantType>(page.getTotal(), page.getRecords()));
    }
}