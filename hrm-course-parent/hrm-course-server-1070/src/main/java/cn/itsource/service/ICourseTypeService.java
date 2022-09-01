package cn.itsource.service;

import cn.itsource.domain.CourseType;
import cn.itsource.result.JSONResult;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 课程目录 服务类
 * </p>
 *
 * @author bobo
 * @since 2022-01-20
 */
public interface ICourseTypeService extends IService<CourseType> {

    JSONResult treeData();

    JSONResult crumbs(Long courseTypeId);

}
