package cn.itsource.controller;

import cn.itsource.service.IConfigService;
import cn.itsource.domain.Config;
import cn.itsource.query.ConfigQuery;
import cn.itsource.result.JSONResult;
import cn.itsource.result.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @desc
 * @author bobo
 * @since 2022-01-16
 */
@RestController
@RequestMapping("/config")
public class ConfigController {

    @Autowired
    public IConfigService configService;

    /**
     * 保存和修改操作公用此方法
     * @param config 前端传递来的实体数据
     */
    @PostMapping(value="/save")
    public JSONResult save(@RequestBody Config config){
        if(config.getId()!=null){
                configService.updateById(config);
        }else{
                configService.insert(config);
        }
        return JSONResult.success();
    }

    /**
    * 根据ID删除指定对象信息
    * @param id
    */
    @DeleteMapping(value="/{id}")
    public JSONResult delete(@PathVariable("id") Long id){
            configService.deleteById(id);
        return JSONResult.success();
    }

    //根据ID查询对象详情信息
    @GetMapping(value = "/{id}")
    public JSONResult get(@PathVariable("id")Long id)
    {
        return JSONResult.success(configService.selectById(id));
    }


    /**
    * 查看所有对象数据（不分页）
    */
    @GetMapping(value = "/list")
    public JSONResult list(){
        return JSONResult.success(configService.selectList(null));
    }


    /**
    * 分页查询数据
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping(value = "/pagelist")
    public JSONResult pageList(@RequestBody ConfigQuery query)
    {
        Page<Config> page = new Page<Config>(query.getPage(),query.getRows());
        page = configService.selectPage(page);
        return JSONResult.success(new PageList<Config>(page.getTotal(), page.getRecords()));
    }
}