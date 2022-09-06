package cn.itsource.controller;

import cn.itsource.domain.VipUser;
import cn.itsource.query.VipUserQuery;
import cn.itsource.result.JSONResult;
import cn.itsource.result.PageList;
import cn.itsource.service.IVipUserService;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @desc
 * @author tangrufei
 * @since 2022-08-19
 */
@RestController
@RequestMapping("/vipUser")
public class VipUserController {

    @Autowired
    public IVipUserService vipUserService;

    /**
     * 保存和修改操作公用此方法
     * @param vipUser 前端传递来的实体数据
     */
    @PostMapping(value="/save")
    public JSONResult save(@RequestBody VipUser vipUser){
        if(vipUser.getId()!=null){
                vipUserService.updateById(vipUser);
        }else{
                vipUserService.insert(vipUser);
        }
        return JSONResult.success();
    }

    /**
    * 根据ID删除指定对象信息
    * @param id
    */
    @DeleteMapping(value="/{id}")
    public JSONResult delete(@PathVariable("id") Long id){
            vipUserService.deleteById(id);
        return JSONResult.success();
    }

    //根据ID查询对象详情信息
    @GetMapping(value = "/{id}")
    public JSONResult get(@PathVariable("id")Long id)
    {
        return JSONResult.success(vipUserService.selectById(id));
    }


    /**
    * 查看所有对象数据（不分页）
    */
    @GetMapping(value = "/list")
    public JSONResult list(){
        return JSONResult.success(vipUserService.selectList(null));
    }


    /**
    * 分页查询数据
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping(value = "/pagelist")
    public JSONResult pageList(@RequestBody VipUserQuery query)
    {
        Page<VipUser> page = new Page<VipUser>(query.getPage(),query.getRows());
        page = vipUserService.selectPage(page);
        return JSONResult.success(new PageList<VipUser>(page.getTotal(), page.getRecords()));
    }
}
