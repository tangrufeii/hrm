package cn.itsource.controller;

import cn.itsource.service.IVipCourseViewService;
import cn.itsource.domain.VipCourseView;
import cn.itsource.query.VipCourseViewQuery;
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
@RequestMapping("/vipCourseView")
public class VipCourseViewController {

    @Autowired
    public IVipCourseViewService vipCourseViewService;

    /**
     * 保存和修改操作公用此方法
     * @param vipCourseView 前端传递来的实体数据
     */
    @PostMapping(value="/save")
    public JSONResult save(@RequestBody VipCourseView vipCourseView){
        if(vipCourseView.getId()!=null){
                vipCourseViewService.updateById(vipCourseView);
        }else{
                vipCourseViewService.insert(vipCourseView);
        }
        return JSONResult.success();
    }

    /**
    * 根据ID删除指定对象信息
    * @param id
    */
    @DeleteMapping(value="/{id}")
    public JSONResult delete(@PathVariable("id") Long id){
            vipCourseViewService.deleteById(id);
        return JSONResult.success();
    }

    //根据ID查询对象详情信息
    @GetMapping(value = "/{id}")
    public JSONResult get(@PathVariable("id")Long id)
    {
        return JSONResult.success(vipCourseViewService.selectById(id));
    }


    /**
    * 查看所有对象数据（不分页）
    */
    @GetMapping(value = "/list")
    public JSONResult list(){
        return JSONResult.success(vipCourseViewService.selectList(null));
    }


    /**
    * 分页查询数据
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping(value = "/pagelist")
    public JSONResult pageList(@RequestBody VipCourseViewQuery query)
    {
        Page<VipCourseView> page = new Page<VipCourseView>(query.getPage(),query.getRows());
        page = vipCourseViewService.selectPage(page);
        return JSONResult.success(new PageList<VipCourseView>(page.getTotal(), page.getRecords()));
    }
}