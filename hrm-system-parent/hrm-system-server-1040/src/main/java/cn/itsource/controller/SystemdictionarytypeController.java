package cn.itsource.controller;

import cn.itsource.service.ISystemdictionarytypeService;
import cn.itsource.domain.Systemdictionarytype;
import cn.itsource.query.SystemdictionarytypeQuery;
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
@RequestMapping("/systemdictionarytype")
public class SystemdictionarytypeController {

    @Autowired
    public ISystemdictionarytypeService systemdictionarytypeService;

    /**
     * 保存和修改操作公用此方法
     * @param systemdictionarytype 前端传递来的实体数据
     */
    @PostMapping(value="/save")
    public JSONResult save(@RequestBody Systemdictionarytype systemdictionarytype){
        if(systemdictionarytype.getId()!=null){
                systemdictionarytypeService.updateById(systemdictionarytype);
        }else{
                systemdictionarytypeService.insert(systemdictionarytype);
        }
        return JSONResult.success();
    }

    /**
    * 根据ID删除指定对象信息
    * @param id
    */
    @DeleteMapping(value="/{id}")
    public JSONResult delete(@PathVariable("id") Long id){
            systemdictionarytypeService.deleteById(id);
        return JSONResult.success();
    }

    //根据ID查询对象详情信息
    @GetMapping(value = "/{id}")
    public JSONResult get(@PathVariable("id")Long id)
    {
        return JSONResult.success(systemdictionarytypeService.selectById(id));
    }


    /**
    * 查看所有对象数据（不分页）
    */
    @GetMapping(value = "/list")
    public JSONResult list(){
        return JSONResult.success(systemdictionarytypeService.selectList(null));
    }


    /**
    * 分页查询数据
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping(value = "/pagelist")
    public JSONResult pageList(@RequestBody SystemdictionarytypeQuery query)
    {
        Page<Systemdictionarytype> page = new Page<Systemdictionarytype>(query.getPage(),query.getRows());
        page = systemdictionarytypeService.selectPage(page);
        return JSONResult.success(new PageList<Systemdictionarytype>(page.getTotal(), page.getRecords()));
    }
}