package cn.itsource.service;

import cn.itsource.domain.Systemdictionarydetail;
import cn.itsource.result.JSONResult;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bobo
 * @since 2022-01-21
 */
public interface ISystemdictionarydetailService extends IService<Systemdictionarydetail> {

    JSONResult listBySn(String type);
}
