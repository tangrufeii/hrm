package cn.itsource.service.impl;

import cn.itsource.domain.VipAddress;
import cn.itsource.mapper.VipAddressMapper;
import cn.itsource.service.IVipAddressService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 收货地址 服务实现类
 * </p>
 *
 * @author Yazi
 * @since 2022-08-16
 */
@Service
public class VipAddressServiceImpl extends ServiceImpl<VipAddressMapper, VipAddress> implements IVipAddressService {

}
