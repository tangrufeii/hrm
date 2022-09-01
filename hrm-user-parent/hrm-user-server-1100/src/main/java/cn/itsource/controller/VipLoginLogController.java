package cn.itsource.controller;

import cn.itsource.service.IVipLoginLogService;
import cn.itsource.domain.VipLoginLog;
import cn.itsource.query.VipLoginLogQuery;
import cn.itsource.result.JSONResult;
import cn.itsource.result.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @desc
 * @author bobo
 * @since 2022-02-12
 */
@RestController
@RequestMapping("/vipLoginLog")
public class VipLoginLogController {

    @Autowired
    public IVipLoginLogService vipLoginLogService;

    /**
     * 保存和修改操作公用此方法
     * @param vipLoginLog 前端传递来的实体数据
     */
    @PostMapping(value="/save")
    public JSONResult save(@RequestBody VipLoginLog vipLoginLog){
        if(vipLoginLog.getId()!=null){
                vipLoginLogService.updateById(vipLoginLog);
        }else{
                vipLoginLogService.insert(vipLoginLog);
        }
        return JSONResult.success();
    }

    /**
    * 根据ID删除指定对象信息
    * @param id
    */
    @DeleteMapping(value="/{id}")
    public JSONResult delete(@PathVariable("id") Long id){
            vipLoginLogService.deleteById(id);
        return JSONResult.success();
    }

    //根据ID查询对象详情信息
    @GetMapping(value = "/{id}")
    public JSONResult get(@PathVariable("id")Long id)
    {
        return JSONResult.success(vipLoginLogService.selectById(id));
    }


    /**
    * 查看所有对象数据（不分页）
    */
    @GetMapping(value = "/list")
    public JSONResult list(){
        return JSONResult.success(vipLoginLogService.selectList(null));
    }


    /**
    * 分页查询数据
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping(value = "/pagelist")
    public JSONResult pageList(@RequestBody VipLoginLogQuery query)
    {
        Page<VipLoginLog> page = new Page<VipLoginLog>(query.getPage(),query.getRows());
        page = vipLoginLogService.selectPage(page);
        return JSONResult.success(new PageList<VipLoginLog>(page.getTotal(), page.getRecords()));
    }
}