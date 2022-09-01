package cn.itsource.service.impl;

import cn.itsource.domain.Config;
import cn.itsource.mapper.ConfigMapper;
import cn.itsource.service.IConfigService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 参数配置表 服务实现类
 * </p>
 *
 * @author bobo
 * @since 2022-01-16
 */
@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements IConfigService {

}
