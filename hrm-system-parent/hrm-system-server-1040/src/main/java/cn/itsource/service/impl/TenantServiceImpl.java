package cn.itsource.service.impl;

import cn.itsource.constants.BaseConstants;
import cn.itsource.domain.Employee;
import cn.itsource.domain.Login;
import cn.itsource.domain.Tenant;
import cn.itsource.dto.LoginMealDto;
import cn.itsource.dto.RegisterDto;
import cn.itsource.exception.MyException;
import cn.itsource.feign.client.LoginFeignClient;
import cn.itsource.mapper.EmployeeMapper;
import cn.itsource.mapper.TenantMapper;
import cn.itsource.result.JSONResult;
import cn.itsource.service.ITenantService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author bobo
 * @since 2022-01-16
 */
@Service
public class TenantServiceImpl extends ServiceImpl<TenantMapper, Tenant> implements ITenantService {

    @Autowired
    private TenantMapper tenantMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private LoginFeignClient loginFeignClient;

    /**
     * 注册逻辑
     * 问题：分布式事务问题？
     */
    @Override
    @GlobalTransactional
    public JSONResult register(RegisterDto dto) {
        /**
         * 1、入参校验
         * 2、t_tenant、t_employee入库
         * 3、t_login_meal、。t_login入库，调用Auth的接口入库，用Feign
         * 4、返回结果
         */
        //校验

        //保存信息到t_login表
        Login login = new Login();
        login.setUsername(dto.getUsername());
        login.setPassword("123456");
        login.setType(0);//0是后台，1是前台
        JSONResult result_1 = loginFeignClient.save(login);
        if(!"0000".equals(result_1.getCode())){
            //调用失败
            throw new MyException("保存Login信息到Auth服务异常");
        }
        String data = (String)result_1.getData();
        Login loginData = JSONObject.parseObject(data, Login.class);
        Long loginId = loginData.getId();
        System.out.println("loginId=" + loginId);

        //保存信息到t_login_meal表
        LoginMealDto loginMealDto = new LoginMealDto();
        loginMealDto.setLoginId(loginId);
        loginMealDto.setMealId(dto.getMealId());
        loginMealDto.setExpireDate(DateUtils.addDays(new Date(), 7));
        JSONResult jsonResult = loginFeignClient.saveLoginMeal(loginMealDto);
        if(!"0000".equals(jsonResult.getCode())){
            //调用失败
            throw new RuntimeException("保存LoginMeal信息到Auth服务异常");
        }

        //保存信息到 t_tenant 表
        Tenant t = dto.getTenant();
        t.setRegisterTime(new Date());
        tenantMapper.insert(t);

        //保存信息到 t_employee 表
        Employee employee = dto.getEmployee();
        employee.setInputTime(new Date());
        employee.setState(BaseConstants.EmployeeConstants.STATUS_EMPLOYEE_0);//正常
        employee.setTenantId(t.getId());//当前员工所属机构ID
        employee.setType(4);//员工类型 ， 1平台普通员工 ，2平台客服人员，3平台管理员，4机构员工，5,机构管理员或其他
        employee.setLoginId(loginId);
        employeeMapper.insert(employee);

        //更新机构表
        t.setAdminId(employee.getId());
        tenantMapper.updateById(t);
        return JSONResult.success();
    }

    @Override
    public JSONResult getTenantByLoginId(Long id) {
        Tenant tenant = tenantMapper.getTenantByLoginId(id);
        return JSONResult.success(JSONObject.toJSON(tenant).toString());
    }
}
