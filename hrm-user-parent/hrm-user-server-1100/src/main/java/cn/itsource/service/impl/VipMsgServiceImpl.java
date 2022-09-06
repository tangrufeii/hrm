package cn.itsource.service.impl;

import cn.itsource.domain.VipMsg;
import cn.itsource.mapper.VipMsgMapper;
import cn.itsource.service.IVipMsgService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 站内信 服务实现类
 * </p>
 *
 * @author Yazi
 * @since 2022-08-16
 */
@Service
public class VipMsgServiceImpl extends ServiceImpl<VipMsgMapper, VipMsg> implements IVipMsgService {

}
