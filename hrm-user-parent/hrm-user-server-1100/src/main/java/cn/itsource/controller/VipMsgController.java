package cn.itsource.controller;

import cn.itsource.service.IVipMsgService;
import cn.itsource.domain.VipMsg;
import cn.itsource.query.VipMsgQuery;
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
@RequestMapping("/vipMsg")
public class VipMsgController {

    @Autowired
    public IVipMsgService vipMsgService;

    /**
     * 保存和修改操作公用此方法
     * @param vipMsg 前端传递来的实体数据
     */
    @PostMapping(value="/save")
    public JSONResult save(@RequestBody VipMsg vipMsg){
        if(vipMsg.getId()!=null){
                vipMsgService.updateById(vipMsg);
        }else{
                vipMsgService.insert(vipMsg);
        }
        return JSONResult.success();
    }

    /**
    * 根据ID删除指定对象信息
    * @param id
    */
    @DeleteMapping(value="/{id}")
    public JSONResult delete(@PathVariable("id") Long id){
            vipMsgService.deleteById(id);
        return JSONResult.success();
    }

    //根据ID查询对象详情信息
    @GetMapping(value = "/{id}")
    public JSONResult get(@PathVariable("id")Long id)
    {
        return JSONResult.success(vipMsgService.selectById(id));
    }


    /**
    * 查看所有对象数据（不分页）
    */
    @GetMapping(value = "/list")
    public JSONResult list(){
        return JSONResult.success(vipMsgService.selectList(null));
    }


    /**
    * 分页查询数据
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping(value = "/pagelist")
    public JSONResult pageList(@RequestBody VipMsgQuery query)
    {
        Page<VipMsg> page = new Page<VipMsg>(query.getPage(),query.getRows());
        page = vipMsgService.selectPage(page);
        return JSONResult.success(new PageList<VipMsg>(page.getTotal(), page.getRecords()));
    }
}