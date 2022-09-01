package cn.itsource.service.impl;

import cn.itsource.domain.VipUser;
import cn.itsource.mapper.VipUserMapper;
import cn.itsource.service.IVipUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员登录账号 服务实现类
 * </p>
 *
 * @author bobo
 * @since 2022-02-12
 */
@Service
public class VipUserServiceImpl extends ServiceImpl<VipUserMapper, VipUser> implements IVipUserService {

}
