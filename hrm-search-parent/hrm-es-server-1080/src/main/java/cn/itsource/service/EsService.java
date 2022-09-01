package cn.itsource.service;

import cn.itsource.doc.CourseDoc;
import cn.itsource.dto.CourseQueryDto;
import cn.itsource.result.PageList;

public interface EsService {

    PageList<CourseDoc> searchCourse(CourseQueryDto dto);
}
