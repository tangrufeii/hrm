package cn.itsource.service.impl;

import cn.itsource.config.rabbit.RabbitMQConfig;
import cn.itsource.doc.CourseDoc;
import cn.itsource.domain.Course;
import cn.itsource.domain.CourseDetail;
import cn.itsource.domain.CourseMarket;
import cn.itsource.domain.Login;
import cn.itsource.dto.CourseAddDto;
import cn.itsource.dto.EmailDto;
import cn.itsource.exception.MyException;
import cn.itsource.feign.client.EsFeignClient;
import cn.itsource.mapper.CourseDetailMapper;
import cn.itsource.mapper.CourseMapper;
import cn.itsource.mapper.CourseMarketMapper;
import cn.itsource.query.CourseQuery;
import cn.itsource.result.JSONResult;
import cn.itsource.service.ICourseService;
import cn.itsource.tools.AuthTools;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author bobo
 * @since 2022-01-20
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private CourseDetailMapper courseDetailMapper;

    @Autowired
    private CourseMarketMapper courseMarketMapper;

    @Override
    @Transactional
    public JSONResult save(CourseAddDto dto) {
        /**
         * 1、t_course：主表
         * 2、t_course_detail
         * 3、t_course_market
         * 4、t_course_resource
         */
        //1、t_course：主表
        Course course = dto.getCourse();
        course.setUserId(42L);
        course.setUserName("yhptest");
        course.setTenantId(26L);
        course.setTenantName("源码时代");
        courseMapper.insert(course);

        //2、t_course_detail
        CourseDetail courseDetail = dto.getCourseDetail();
        courseDetail.setId(course.getId());
        courseDetailMapper.insert(courseDetail);

        //3、t_course_market
        CourseMarket courseMarket = dto.getCourseMarket();
        courseMarket.setId(course.getId());
        courseMarketMapper.insert(courseMarket);

        return JSONResult.success();
    }

    @Override
    public Page<CourseAddDto> selectMyPage(CourseQuery query) {
        /**
         * 返回三张表数据
         */
        Page<CourseAddDto> page = new Page<>(query.getPage(), query.getRows());
        List<CourseAddDto> list = courseMapper.selectMyPage(page, query);
        return page.setRecords(list);
    }

    @Override
    public JSONResult updateMyData(CourseAddDto dto) {
        /**
         * 1、t_course：主表
         * 2、t_course_detail
         * 3、t_course_market
         * 4、t_course_resource
         */
        //1、t_course：主表
        Login login = AuthTools.getLoginInfo();
        Course course = dto.getCourse();
        course.setUserId(login.getId());
        course.setUserName(login.getUsername());

        /**
         * 思路根据上面的登陆人ID，通过feign调用system微服务，获取机构信息
         */
        course.setTenantId(26L);
        course.setTenantName("源码时代");
        courseMapper.updateById(course);

        //2、t_course_detail
        CourseDetail courseDetail = dto.getCourseDetail();
        courseDetail.setId(course.getId());
        courseDetailMapper.updateById(courseDetail);

        //3、t_course_market
        CourseMarket courseMarket = dto.getCourseMarket();
        courseMarket.setId(course.getId());
        courseMarketMapper.updateById(courseMarket);

        return JSONResult.success();
    }

    @Autowired
    private EsFeignClient esFeignClient;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    //@Transactional
    public JSONResult onLineCourse(CourseQuery query) {
        /**
         * 1、校验入参
         * 2、根据ID更新数据库记录，将状态由线下改为上线
         * 3、将课程信息存入ES中
         */
        //根据ID更新数据库记录，将状态由线下改为上线
        List<Course> courseList = courseMapper.selectBatchIds(Arrays.asList(query.getIds()));
        CourseDoc doc  = null;
        List<CourseDoc> list = new ArrayList<>();
        for (int i = 0; i < courseList.size(); i++) {
            Course course = courseList.get(i);
            //课程状态，下线：0 ， 上线：1
            if(course.getStatus() == 0){
                //进入到此处，说明该课程需要做课程上线
                course.setStatus(1);
                courseMapper.updateById(course);

                //调用ES保存课程信息
                doc  = new CourseDoc();
                BeanUtils.copyProperties(course, doc);
                CourseMarket courseMarket = courseMarketMapper.selectById(course.getId());
                BeanUtils.copyProperties(courseMarket, doc);
                list.add(doc);
            }
        }
        //调用接口批量保存课程信息到ES中
        JSONResult jsonResult = esFeignClient.insertBatch(list);
        if(!"0000".equals(jsonResult.getCode())){
            //保存失败
            throw new MyException(jsonResult.getMessage());
        }

        //上线课程成功后，发布营销信息到每位用户邮箱
        EmailDto dto = new EmailDto();
        List<String> emailList = new ArrayList<>();
        //真实场景去查询数据库，获取需要接受此邮件的所有用户邮箱地址
        emailList.add("234234324@qq,com");
        emailList.add("43456456@qq,com");
        emailList.add("456456456@qq,com");
        emailList.add("56756756765@qq,com");
        dto.setEmailAddress(emailList);
        dto.setTitle("课程上新了....");
        dto.setContent("Java大神课上线啦，赶紧抢购，前20名，免费领取，赶紧戳我：<a href='http://www.baidu.com'>源码官网</a>");

        rabbitTemplate.convertAndSend(RabbitMQConfig.DEFAULT_EXCHANGE, "course.email", JSONObject.toJSONString(dto));
        return JSONResult.success();
    }

    //下线
    @Override
    public JSONResult offLineCourse(CourseQuery query) {
        /**
         * 1、校验入参
         * 2、根据ID更新数据库记录，将状态由线下改为上线
         * 3、将课程信息存入ES中
         */
        //根据ID更新数据库记录，将状态由线上线为下线
        List<Course> courseList = courseMapper.selectBatchIds(Arrays.asList(query.getIds()));
        CourseDoc doc  = null;
        List<CourseDoc> list = new ArrayList<>();
        for (int i = 0; i < courseList.size(); i++) {
            Course course = courseList.get(i);
            //课程状态，下线：0 ， 上线：1
            if(course.getStatus() == 1){
                //进入到此处，说明该课程需要做课程下线
                course.setStatus(0);
                courseMapper.updateById(course);

                //调用ES保存课程信息
                doc  = new CourseDoc();
                BeanUtils.copyProperties(course, doc);
                CourseMarket courseMarket = courseMarketMapper.selectById(course.getId());
                BeanUtils.copyProperties(courseMarket, doc);
                list.add(doc);
            }
        }
        //调用接口从ES中删除课程数据
        JSONResult jsonResult = esFeignClient.deleteBatch(list);
        if(!"0000".equals(jsonResult.getCode())){
            //从ES中删除数据失败
            throw new MyException(jsonResult.getMessage());
        }
        return JSONResult.success();
    }
}
