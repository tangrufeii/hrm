package cn.itsource.controller;

import cn.itsource.domain.Login;
import cn.itsource.dto.LoginMealDto;
import cn.itsource.query.LoginQuery;
import cn.itsource.result.JSONResult;
import cn.itsource.result.PageList;
import cn.itsource.service.ILoginService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    public ILoginService loginService;

    //客户端模式直接获取token
    @GetMapping(value="/getToken")
    public JSONResult getToken(){
        // 创建 ClientCredentialsResourceDetails 对象
        ClientCredentialsResourceDetails resourceDetails = new ClientCredentialsResourceDetails();
        resourceDetails.setAccessTokenUri("http://localhost:1050/oauth/token");
        resourceDetails.setClientId("admin");
        resourceDetails.setClientSecret("123456");
        // 创建 OAuth2RestTemplate 对象
        OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(resourceDetails);
        restTemplate.setAccessTokenProvider(new ClientCredentialsAccessTokenProvider());
        // 获取访问令牌
        return JSONResult.success(restTemplate.getAccessToken());
    }

    /**
     * 系统登录接口
     */
    @PostMapping(value="/user")
    public JSONResult user(@RequestBody Login login){
        return loginService.user(login);
    }

    /**
     * 保存saveLoginMeal
     */
    @PostMapping(value="/saveLoginMeal")
    public JSONResult saveLoginMeal(@RequestBody LoginMealDto loginMealDto){
        return loginService.saveLoginMeal(loginMealDto);
    }

    /**
     * 保存和修改操作公用此方法
     * @param login 前端传递来的实体数据
     */
    @PostMapping(value="/save")
    public JSONResult save(@RequestBody Login login){
        if(login.getId()!=null){
                loginService.updateById(login);
        }else{
                loginService.insert(login);
        }
        return JSONResult.success(JSONObject.toJSONString(login));
    }

    /**
    * 根据ID删除指定对象信息
    * @param id
    */
    @DeleteMapping(value="/{id}")
    public JSONResult delete(@PathVariable("id") Long id){
            loginService.deleteById(id);
        return JSONResult.success();
    }

    //根据ID查询对象详情信息
    @GetMapping(value = "/{id}")
    public JSONResult get(@PathVariable("id")Long id)
    {
        return JSONResult.success(loginService.selectById(id));
    }


    /**
    * 查看所有对象数据（不分页）
    */
    @GetMapping(value = "/list")
    public JSONResult list(){
        return JSONResult.success(loginService.selectList(null));
    }


    /**
    * 分页查询数据
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping(value = "/pagelist")
    public JSONResult pageList(@RequestBody LoginQuery query)
    {
        Page<Login> page = new Page<Login>(query.getPage(),query.getRows());
        page = loginService.selectPage(page);
        return JSONResult.success(new PageList<Login>(page.getTotal(), page.getRecords()));
    }
}
