package cn.itsource.mapper;

import cn.itsource.domain.Permission;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * @desc desc 权限接口
 * @author tangrufei
 * @date 2022/8/31 13:07
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    List<Permission> selectPermissionsByUserId(Long id);
}
