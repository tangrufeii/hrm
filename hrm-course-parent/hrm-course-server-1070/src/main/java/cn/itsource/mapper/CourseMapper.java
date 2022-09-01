package cn.itsource.mapper;

import cn.itsource.domain.Course;
import cn.itsource.dto.CourseAddDto;
import cn.itsource.query.CourseQuery;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Yazi
 * @since 2022-08-22
 */
public interface CourseMapper extends BaseMapper<Course> {

    List<CourseAddDto> selectMyPage(Page<CourseAddDto> page, CourseQuery query);
}
