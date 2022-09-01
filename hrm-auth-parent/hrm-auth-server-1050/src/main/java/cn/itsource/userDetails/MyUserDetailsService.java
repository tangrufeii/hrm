package cn.itsource.userDetails;

import cn.itsource.domain.Login;
import cn.itsource.domain.Permission;
import cn.itsource.domain.Tenant;
import cn.itsource.exception.MyException;
import cn.itsource.feign.client.TenantFeign;
import cn.itsource.mapper.LoginMapper;
import cn.itsource.mapper.PermissionMapper;
import cn.itsource.result.JSONResult;
import com.alibaba.fastjson.JSONObject;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

//自定义UserDetailsService
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Resource
    private LoginMapper loginMapper;

    @Resource
    private PermissionMapper permissionMapper;

    @Resource
    private TenantFeign tenantFeign;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //1.username基本判断
        if(!StringUtils.hasLength(username)){
            throw new UsernameNotFoundException("用户名不可为空");
        }
        //2.根据username查询mysql中的认证信息
        Login dbUser = loginMapper.selectByUsername(username);
        if(dbUser == null){
            throw new UsernameNotFoundException("用户名不存在");
        }

        JSONResult tenantInfo = tenantFeign.getTenantByLoginId(dbUser.getId());
        if(!"0000".equals(tenantInfo.getCode())){
            throw new MyException(tenantInfo.getMessage());
        }
        String tenantJson = (String)tenantInfo.getData();
        Tenant tenant = JSONObject.parseObject(tenantJson, Tenant.class);

        dbUser.setTenantId(tenant.getId());
        dbUser.setTenantName(tenant.getCompanyName());

        //3.加载用户的权限列表
        List<GrantedAuthority> authorities = new ArrayList<>();
        List<Permission> permissions = permissionMapper.selectPermissionsByUserId(dbUser.getId());
        for (Permission permission : permissions){
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(permission.getSn());
            System.out.println("加载用户【" + username + "】的权限：" + permission.getSn());
            authorities.add(simpleGrantedAuthority);
        }

        //4.把认证信息和权限信息封装成UserDetails返回
        User userDetails = new User(
                JSONObject.toJSONString(dbUser), //账号
                dbUser.getPassword(), //密码
                dbUser.getEnabled(),  //用户是否已启用
                dbUser.getAccountNonExpired(),  //账户是否过期
                dbUser.getCredentialsNonExpired(), //密码是否过期
                dbUser.getAccountNonLocked(), //账户是否锁定
                authorities); //用户拥有权限集合
        return userDetails;
    }
}
