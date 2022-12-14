package cn.itsource.mapper;

import cn.itsource.domain.Systemdictionarydetail;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Yazi
 * @since 2022-08-16
 */
public interface SystemdictionarydetailMapper extends BaseMapper<Systemdictionarydetail> {

    List<Systemdictionarydetail> listBySn(String type);
}
