package cn.itsource.controller;

import cn.itsource.service.IVipRealInfoService;
import cn.itsource.domain.VipRealInfo;
import cn.itsource.query.VipRealInfoQuery;
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
@RequestMapping("/vipRealInfo")
public class VipRealInfoController {

    @Autowired
    public IVipRealInfoService vipRealInfoService;

    /**
     * 保存和修改操作公用此方法
     * @param vipRealInfo 前端传递来的实体数据
     */
    @PostMapping(value="/save")
    public JSONResult save(@RequestBody VipRealInfo vipRealInfo){
        if(vipRealInfo.getId()!=null){
                vipRealInfoService.updateById(vipRealInfo);
        }else{
                vipRealInfoService.insert(vipRealInfo);
        }
        return JSONResult.success();
    }

    /**
    * 根据ID删除指定对象信息
    * @param id
    */
    @DeleteMapping(value="/{id}")
    public JSONResult delete(@PathVariable("id") Long id){
            vipRealInfoService.deleteById(id);
        return JSONResult.success();
    }

    //根据ID查询对象详情信息
    @GetMapping(value = "/{id}")
    public JSONResult get(@PathVariable("id")Long id)
    {
        return JSONResult.success(vipRealInfoService.selectById(id));
    }


    /**
    * 查看所有对象数据（不分页）
    */
    @GetMapping(value = "/list")
    public JSONResult list(){
        return JSONResult.success(vipRealInfoService.selectList(null));
    }


    /**
    * 分页查询数据
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping(value = "/pagelist")
    public JSONResult pageList(@RequestBody VipRealInfoQuery query)
    {
        Page<VipRealInfo> page = new Page<VipRealInfo>(query.getPage(),query.getRows());
        page = vipRealInfoService.selectPage(page);
        return JSONResult.success(new PageList<VipRealInfo>(page.getTotal(), page.getRecords()));
    }
}