package cn.itsource.controller;

import cn.itsource.service.ISystemdictionarydetailService;
import cn.itsource.domain.Systemdictionarydetail;
import cn.itsource.query.SystemdictionarydetailQuery;
import cn.itsource.result.JSONResult;
import cn.itsource.result.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @desc
 * @author bobo
 * @since 2022-01-21
 */
@RestController
@RequestMapping("/systemdictionarydetail")
public class SystemdictionarydetailController {

    @Autowired
    public ISystemdictionarydetailService systemdictionarydetailService;

    @GetMapping("/listBySn/{type}")
    public JSONResult listBySn(@PathVariable("type") String type){
        return systemdictionarydetailService.listBySn(type);
    }

    /**
     * 保存和修改操作公用此方法
     * @param systemdictionarydetail 前端传递来的实体数据
     */
    @PostMapping(value="/save")
    public JSONResult save(@RequestBody Systemdictionarydetail systemdictionarydetail){
        if(systemdictionarydetail.getId()!=null){
                systemdictionarydetailService.updateById(systemdictionarydetail);
        }else{
                systemdictionarydetailService.insert(systemdictionarydetail);
        }
        return JSONResult.success();
    }

    /**
    * 根据ID删除指定对象信息
    * @param id
    */
    @DeleteMapping(value="/{id}")
    public JSONResult delete(@PathVariable("id") Long id){
            systemdictionarydetailService.deleteById(id);
        return JSONResult.success();
    }

    //根据ID查询对象详情信息
    @GetMapping(value = "/{id}")
    public JSONResult get(@PathVariable("id")Long id)
    {
        return JSONResult.success(systemdictionarydetailService.selectById(id));
    }


    /**
    * 查看所有对象数据（不分页）
    */
    @GetMapping(value = "/list")
    public JSONResult list(){
        return JSONResult.success(systemdictionarydetailService.selectList(null));
    }


    /**
    * 分页查询数据
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping(value = "/pagelist")
    public JSONResult pageList(@RequestBody SystemdictionarydetailQuery query)
    {
        Page<Systemdictionarydetail> page = new Page<Systemdictionarydetail>(query.getPage(),query.getRows());
        page = systemdictionarydetailService.selectPage(page);
        return JSONResult.success(new PageList<Systemdictionarydetail>(page.getTotal(), page.getRecords()));
    }
}