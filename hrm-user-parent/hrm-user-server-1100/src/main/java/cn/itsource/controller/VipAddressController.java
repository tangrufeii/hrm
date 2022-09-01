package cn.itsource.controller;

import cn.itsource.service.IVipAddressService;
import cn.itsource.domain.VipAddress;
import cn.itsource.query.VipAddressQuery;
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
@RequestMapping("/vipAddress")
public class VipAddressController {

    @Autowired
    public IVipAddressService vipAddressService;

    /**
     * 保存和修改操作公用此方法
     * @param vipAddress 前端传递来的实体数据
     */
    @PostMapping(value="/save")
    public JSONResult save(@RequestBody VipAddress vipAddress){
        if(vipAddress.getId()!=null){
                vipAddressService.updateById(vipAddress);
        }else{
                vipAddressService.insert(vipAddress);
        }
        return JSONResult.success();
    }

    /**
    * 根据ID删除指定对象信息
    * @param id
    */
    @DeleteMapping(value="/{id}")
    public JSONResult delete(@PathVariable("id") Long id){
            vipAddressService.deleteById(id);
        return JSONResult.success();
    }

    //根据ID查询对象详情信息
    @GetMapping(value = "/{id}")
    public JSONResult get(@PathVariable("id")Long id)
    {
        return JSONResult.success(vipAddressService.selectById(id));
    }


    /**
    * 查看所有对象数据（不分页）
    */
    @GetMapping(value = "/list")
    public JSONResult list(){
        return JSONResult.success(vipAddressService.selectList(null));
    }


    /**
    * 分页查询数据
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping(value = "/pagelist")
    public JSONResult pageList(@RequestBody VipAddressQuery query)
    {
        Page<VipAddress> page = new Page<VipAddress>(query.getPage(),query.getRows());
        page = vipAddressService.selectPage(page);
        return JSONResult.success(new PageList<VipAddress>(page.getTotal(), page.getRecords()));
    }
}