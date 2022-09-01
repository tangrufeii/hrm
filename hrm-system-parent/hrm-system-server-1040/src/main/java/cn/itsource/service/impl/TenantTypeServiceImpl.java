package cn.itsource.service.impl;

import cn.itsource.domain.TenantType;
import cn.itsource.mapper.TenantTypeMapper;
import cn.itsource.service.ITenantTypeService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 租户(机构)类型表 服务实现类
 * </p>
 *
 * @author bobo
 * @since 2022-01-16
 */
@Service
public class TenantTypeServiceImpl extends ServiceImpl<TenantTypeMapper, TenantType> implements ITenantTypeService {

}
