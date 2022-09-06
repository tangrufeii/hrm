package cn.itsource.controller;

import cn.itsource.service.IVipBaseService;
import cn.itsource.domain.VipBase;
import cn.itsource.query.VipBaseQuery;
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
@RequestMapping("/vipBase")
public class VipBaseController {

    @Autowired
    public IVipBaseService vipBaseService;

    /**
     * 保存和修改操作公用此方法
     * @param vipBase 前端传递来的实体数据
     */
    @PostMapping(value="/save")
    public JSONResult save(@RequestBody VipBase vipBase){
        if(vipBase.getId()!=null){
                vipBaseService.updateById(vipBase);
        }else{
                vipBaseService.insert(vipBase);
        }
        return JSONResult.success();
    }

    /**
    * 根据ID删除指定对象信息
    * @param id
    */
    @DeleteMapping(value="/{id}")
    public JSONResult delete(@PathVariable("id") Long id){
            vipBaseService.deleteById(id);
        return JSONResult.success();
    }

    //根据ID查询对象详情信息
    @GetMapping(value = "/{id}")
    public JSONResult get(@PathVariable("id")Long id)
    {
        return JSONResult.success(vipBaseService.selectById(id));
    }


    /**
    * 查看所有对象数据（不分页）
    */
    @GetMapping(value = "/list")
    public JSONResult list(){
        return JSONResult.success(vipBaseService.selectList(null));
    }


    /**
    * 分页查询数据
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping(value = "/pagelist")
    public JSONResult pageList(@RequestBody VipBaseQuery query)
    {
        Page<VipBase> page = new Page<VipBase>(query.getPage(),query.getRows());
        page = vipBaseService.selectPage(page);
        return JSONResult.success(new PageList<VipBase>(page.getTotal(), page.getRecords()));
    }
}