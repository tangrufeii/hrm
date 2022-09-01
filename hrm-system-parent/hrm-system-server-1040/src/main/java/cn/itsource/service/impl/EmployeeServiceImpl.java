package cn.itsource.service.impl;

import cn.itsource.domain.Employee;
import cn.itsource.mapper.EmployeeMapper;
import cn.itsource.service.IEmployeeService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author bobo
 * @since 2022-01-16
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

}
