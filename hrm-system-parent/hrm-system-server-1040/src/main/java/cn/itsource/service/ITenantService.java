package cn.itsource.service;

import cn.itsource.domain.Tenant;
import cn.itsource.dto.RegisterDto;
import cn.itsource.result.JSONResult;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bobo
 * @since 2022-01-16
 */
public interface ITenantService extends IService<Tenant> {

    JSONResult register(RegisterDto dto);

    JSONResult getTenantByLoginId(Long id);
}
