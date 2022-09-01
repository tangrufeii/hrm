package cn.itsource.controller;

import cn.itsource.service.IVipCourseCollectService;
import cn.itsource.domain.VipCourseCollect;
import cn.itsource.query.VipCourseCollectQuery;
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
@RequestMapping("/vipCourseCollect")
public class VipCourseCollectController {

    @Autowired
    public IVipCourseCollectService vipCourseCollectService;

    /**
     * 保存和修改操作公用此方法
     * @param vipCourseCollect 前端传递来的实体数据
     */
    @PostMapping(value="/save")
    public JSONResult save(@RequestBody VipCourseCollect vipCourseCollect){
        if(vipCourseCollect.getId()!=null){
                vipCourseCollectService.updateById(vipCourseCollect);
        }else{
                vipCourseCollectService.insert(vipCourseCollect);
        }
        return JSONResult.success();
    }

    /**
    * 根据ID删除指定对象信息
    * @param id
    */
    @DeleteMapping(value="/{id}")
    public JSONResult delete(@PathVariable("id") Long id){
            vipCourseCollectService.deleteById(id);
        return JSONResult.success();
    }

    //根据ID查询对象详情信息
    @GetMapping(value = "/{id}")
    public JSONResult get(@PathVariable("id")Long id)
    {
        return JSONResult.success(vipCourseCollectService.selectById(id));
    }


    /**
    * 查看所有对象数据（不分页）
    */
    @GetMapping(value = "/list")
    public JSONResult list(){
        return JSONResult.success(vipCourseCollectService.selectList(null));
    }


    /**
    * 分页查询数据
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping(value = "/pagelist")
    public JSONResult pageList(@RequestBody VipCourseCollectQuery query)
    {
        Page<VipCourseCollect> page = new Page<VipCourseCollect>(query.getPage(),query.getRows());
        page = vipCourseCollectService.selectPage(page);
        return JSONResult.success(new PageList<VipCourseCollect>(page.getTotal(), page.getRecords()));
    }
}