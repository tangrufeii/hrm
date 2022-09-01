package cn.itsource.controller;

import cn.itsource.domain.Tenant;
import cn.itsource.dto.RegisterDto;
import cn.itsource.exception.MyException;
import cn.itsource.query.TenantQuery;
import cn.itsource.result.JSONResult;
import cn.itsource.result.PageList;
import cn.itsource.service.ITenantService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @desc
 * @author bobo
 * @since 2022-01-16
 */
@RestController
@RequestMapping("/tenant")
public class TenantController {

    @Autowired
    public ITenantService tenantService;

    //根据登录人ID获取所属机构信息
    @GetMapping(value = "/getTenantByLoginId/{id}")
    public JSONResult getTenantByLoginId(@PathVariable("id") Long id)
    {
        return tenantService.getTenantByLoginId(id);
    }

    /**
     * 机构入驻，注册
     */
    @PostMapping(value="/register")
    public JSONResult register(@RequestBody RegisterDto dto){
        return tenantService.register(dto);
    }

    /**
     * 保存和修改操作公用此方法
     * @param tenant 前端传递来的实体数据
     */
    @PostMapping(value="/save")
    public JSONResult save(@RequestBody Tenant tenant){
        if(tenant.getId()!=null){
                tenantService.updateById(tenant);
        }else{
                tenantService.insert(tenant);
        }
        return JSONResult.success();
    }

    /**
    * 根据ID删除指定对象信息
    * @param id
    */
    @DeleteMapping(value="/{id}")
    public JSONResult delete(@PathVariable("id") Long id){
            tenantService.deleteById(id);
        return JSONResult.success();
    }

    //根据ID查询对象详情信息
    @GetMapping(value = "/{id}")
    public JSONResult get(@PathVariable("id")Long id)
    {
        return JSONResult.success(tenantService.selectById(id));
    }


    /**
    * 查看所有对象数据（不分页）
    */
    @GetMapping(value = "/list")
    public JSONResult list(){
        return JSONResult.success(tenantService.selectList(null));
    }


    /**
    * 分页查询数据
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping(value = "/pagelist")
    public JSONResult pageList(@RequestBody TenantQuery query)
    {
        //try {
            int i = 1-1;
            if(i == 0){
                throw new MyException("123");
            }
            Page<Tenant> page = new Page<Tenant>(query.getPage(),query.getRows());
            Wrapper wrapper = new EntityWrapper();
            wrapper.like("company_name", query.getKeyword())
                    .or()
                    .like("address", query.getKeyword())
                    .or()
                    .like("logo", query.getKeyword());
            page = tenantService.selectPage(page, wrapper);
            return JSONResult.success(new PageList<Tenant>(page.getTotal(), page.getRecords()));
        //}catch (NullPointerException e){
        //    e.printStackTrace();
        //    return JSONResult.error("发生空指针异常");
        //}catch (Exception e){
        //    e.printStackTrace();
        //    return JSONResult.error("查询发生异常");
        //}
    }
}
