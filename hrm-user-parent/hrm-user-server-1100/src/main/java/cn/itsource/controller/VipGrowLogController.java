package cn.itsource.controller;

import cn.itsource.service.IVipGrowLogService;
import cn.itsource.domain.VipGrowLog;
import cn.itsource.query.VipGrowLogQuery;
import cn.itsource.result.JSONResult;
import cn.itsource.result.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @desc
 * @author tangrufei
 * @since 2022-08-19
 */
@RestController
@RequestMapping("/vipGrowLog")
public class VipGrowLogController {

    @Autowired
    public IVipGrowLogService vipGrowLogService;

    /**
     * 保存和修改操作公用此方法
     * @param vipGrowLog 前端传递来的实体数据
     */
    @PostMapping(value="/save")
    public JSONResult save(@RequestBody VipGrowLog vipGrowLog){
        if(vipGrowLog.getId()!=null){
                vipGrowLogService.updateById(vipGrowLog);
        }else{
                vipGrowLogService.insert(vipGrowLog);
        }
        return JSONResult.success();
    }

    /**
    * 根据ID删除指定对象信息
    * @param id
    */
    @DeleteMapping(value="/{id}")
    public JSONResult delete(@PathVariable("id") Long id){
            vipGrowLogService.deleteById(id);
        return JSONResult.success();
    }

    //根据ID查询对象详情信息
    @GetMapping(value = "/{id}")
    public JSONResult get(@PathVariable("id")Long id)
    {
        return JSONResult.success(vipGrowLogService.selectById(id));
    }


    /**
    * 查看所有对象数据（不分页）
    */
    @GetMapping(value = "/list")
    public JSONResult list(){
        return JSONResult.success(vipGrowLogService.selectList(null));
    }


    /**
    * 分页查询数据
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping(value = "/pagelist")
    public JSONResult pageList(@RequestBody VipGrowLogQuery query)
    {
        Page<VipGrowLog> page = new Page<VipGrowLog>(query.getPage(),query.getRows());
        page = vipGrowLogService.selectPage(page);
        return JSONResult.success(new PageList<VipGrowLog>(page.getTotal(), page.getRecords()));
    }
}