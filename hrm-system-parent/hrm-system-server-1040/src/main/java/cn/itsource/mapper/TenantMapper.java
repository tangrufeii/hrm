package cn.itsource.mapper;

import cn.itsource.domain.Tenant;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Yazi
 * @since 2022-08-16
 */
public interface TenantMapper extends BaseMapper<Tenant> {

    Tenant getTenantByLoginId(Long id);
}
