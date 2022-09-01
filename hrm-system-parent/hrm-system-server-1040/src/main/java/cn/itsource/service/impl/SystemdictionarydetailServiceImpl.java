package cn.itsource.service.impl;

import cn.itsource.domain.Systemdictionarydetail;
import cn.itsource.mapper.SystemdictionarydetailMapper;
import cn.itsource.result.JSONResult;
import cn.itsource.service.ISystemdictionarydetailService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author bobo
 * @since 2022-01-21
 */
@Service
public class SystemdictionarydetailServiceImpl extends ServiceImpl<SystemdictionarydetailMapper, Systemdictionarydetail> implements ISystemdictionarydetailService {

    @Autowired
    private SystemdictionarydetailMapper systemdictionarydetailMapper;
    /**
     * 根据类型获取数据详情
     * @param type
     * @return
     */
    @Override
    public JSONResult listBySn(String type) {
        List<Systemdictionarydetail> list = systemdictionarydetailMapper.listBySn(type);
        return JSONResult.success(list);
    }
}
