package cn.itsource.dto;

import cn.itsource.domain.Course;
import cn.itsource.domain.CourseDetail;
import cn.itsource.domain.CourseMarket;
import lombok.Data;

/**
 * @description: 课程新增实体对象
 * @auth: tangrufei
 * @date: 2022-08-21 14:33
 */
@Data
public class CourseAddDto {

    private Course course;
    private CourseDetail courseDetail;
    private CourseMarket courseMarket;
}