package cn.itsource.service.impl;

import cn.itsource.domain.Permission;
import cn.itsource.mapper.PermissionMapper;
import cn.itsource.service.IPermissionService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
/**
 * @desc desc 权限
 * @author tangrufei
 * @date 2022/8/31 13:34
 * @param null
 * @return null
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

}
