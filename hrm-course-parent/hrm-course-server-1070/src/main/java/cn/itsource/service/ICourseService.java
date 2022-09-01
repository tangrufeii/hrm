package cn.itsource.service;

import cn.itsource.domain.Course;
import cn.itsource.dto.CourseAddDto;
import cn.itsource.query.CourseQuery;
import cn.itsource.result.JSONResult;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bobo
 * @since 2022-01-20
 */
public interface ICourseService extends IService<Course> {

    JSONResult save(CourseAddDto dto);

    Page<CourseAddDto> selectMyPage(CourseQuery query);

    JSONResult updateMyData(CourseAddDto dto);

    JSONResult onLineCourse(CourseQuery query);

    JSONResult offLineCourse(CourseQuery query);
}
