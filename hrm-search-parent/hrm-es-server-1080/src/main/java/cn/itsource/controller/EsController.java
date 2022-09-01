package cn.itsource.controller;

import cn.itsource.doc.CourseDoc;
import cn.itsource.dto.CourseQueryDto;
import cn.itsource.repo.MyElasticsearchRepository;
import cn.itsource.result.JSONResult;
import cn.itsource.result.PageList;
import cn.itsource.service.EsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @description:
 * @auth: tangrufei
 * @date: 2022-08-23 10:43
 */
@RestController
@RequestMapping("/es")
public class EsController {

    @Autowired
    private MyElasticsearchRepository repo;

    @Autowired
    private EsService esService;

    /**
     * 课程搜索
     */
    @PostMapping("/searchCourse")
    public JSONResult searchCourse(@RequestBody CourseQueryDto dto){
        PageList<CourseDoc> courseDocPageList = esService.searchCourse(dto);
        return JSONResult.success(courseDocPageList);
    }

    /**
     * 往ES中存数据(单条)
     */
    @PostMapping("/saveCourse")
    public JSONResult insert(@RequestBody CourseDoc doc){
        //保存到ES中去
        repo.save(doc);
        repo.delete(doc);
        return JSONResult.success();
    }

    /**
     * 往ES中存数据(批量)
     */
    @PostMapping("/saveCourseBatch")
    @PreAuthorize("hasAuthority('es:saveCourse')")
    public JSONResult insertBatch(@RequestBody List<CourseDoc> list){
        System.out.println("ES服务-课程批量上线");
        //保存到ES中去
        repo.saveAll(list);
        return JSONResult.success();
    }

    /**
     * 从ES中删除数据(批量)
     */
    @PostMapping("/deleteCourseBatch")
    public JSONResult deleteBatch(@RequestBody List<CourseDoc> list){
        //保存到ES中去
        repo.deleteAll(list);
        return JSONResult.success();
    }
}
