package cn.itsource.controller;

import cn.itsource.service.IPostitionService;
import cn.itsource.domain.Postition;
import cn.itsource.query.PostitionQuery;
import cn.itsource.result.JSONResult;
import cn.itsource.result.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/postition")
public class PostitionController {

    @Autowired
    public IPostitionService postitionService;

    /**
     * 保存和修改操作公用此方法
     * @param postition 前端传递来的实体数据
     */
    @PostMapping(value="/save")
    public JSONResult save(@RequestBody Postition postition){
        if(postition.getId()!=null){
                postitionService.updateById(postition);
        }else{
                postitionService.insert(postition);
        }
        return JSONResult.success();
    }

    /**
    * 根据ID删除指定对象信息
    * @param id
    */
    @DeleteMapping(value="/{id}")
    public JSONResult delete(@PathVariable("id") Long id){
            postitionService.deleteById(id);
        return JSONResult.success();
    }

    //根据ID查询对象详情信息
    @GetMapping(value = "/{id}")
    public JSONResult get(@PathVariable("id")Long id)
    {
        return JSONResult.success(postitionService.selectById(id));
    }


    /**
    * 查看所有对象数据（不分页）
    */
    @GetMapping(value = "/list")
    public JSONResult list(){
        return JSONResult.success(postitionService.selectList(null));
    }


    /**
    * 分页查询数据
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping(value = "/pagelist")
    public JSONResult pageList(@RequestBody PostitionQuery query)
    {
        Page<Postition> page = new Page<Postition>(query.getPage(),query.getRows());
        page = postitionService.selectPage(page);
        return JSONResult.success(new PageList<Postition>(page.getTotal(), page.getRecords()));
    }
}