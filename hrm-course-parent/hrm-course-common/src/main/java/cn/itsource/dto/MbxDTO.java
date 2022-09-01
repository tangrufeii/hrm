package cn.itsource.dto;

import cn.itsource.domain.CourseType;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: 面包屑数据对象
 * @auth: tangrufei
 * @date: 2022-08-21 10:22
 */
@Data
public class MbxDTO {

    private CourseType ownerCourseType;//对象

    private List<CourseType> otherCourseTypes = new ArrayList<>();//List集合
}
