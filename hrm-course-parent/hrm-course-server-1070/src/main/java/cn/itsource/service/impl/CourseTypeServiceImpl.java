package cn.itsource.service.impl;

import cn.itsource.constants.BaseConstants;
import cn.itsource.domain.CourseType;
import cn.itsource.dto.MbxDTO;
import cn.itsource.mapper.CourseTypeMapper;
import cn.itsource.result.JSONResult;
import cn.itsource.service.ICourseTypeService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * <p>
 * 课程目录 服务实现类
 * </p>
 *
 * @author bobo
 * @since 2022-01-20
 */
@Service
@Slf4j
public class CourseTypeServiceImpl extends ServiceImpl<CourseTypeMapper, CourseType> implements ICourseTypeService {

    @Autowired
    private CourseTypeMapper courseTypeMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 1、查询所有课程分类数据
     * 2、找出第一级分类
     * 3、其他级的分类只需要找到自己的父节点，然后将自己放入父节点的children
     */
    @Override
    @Cacheable(cacheNames = "courseType", key = "'courseTypeTree'")
    public JSONResult treeData() {
        log.info("treeData  执行了");
        /**
         * 1、查询缓存，看是否存在课程分类数据
         * 2、如果存在，直接返回数据，不需要查询数据库
         * 3、如果不存在，查询数据库，并将数据存到缓存
         * 4、数据返回前端
         */
        //缓存中没有数据，查询数据库，并将数据存到缓存
        //查询所有课程分类数据
        List<CourseType> courseTypeList = courseTypeMapper.selectList(null);

        List<CourseType> firstNodeList = new ArrayList<>();//只存放一级节点

        Map<Long, CourseType> map = new HashMap<>();
        for (int k = 0; k < courseTypeList.size(); k++) {
            CourseType courseType = courseTypeList.get(k);
            map.put(courseType.getId(), courseType);
        }

        if(!CollectionUtils.isEmpty(courseTypeList)){
            for (int i = 0; i < courseTypeList.size(); i++) {
                CourseType courseType = courseTypeList.get(i);//pid:1037
                if(courseType.getPid().longValue() == 0){
                    //表示当前节点为第一级节点
                    firstNodeList.add(courseType);
                }
                else{
                    //说明当前节点非一级
                    //寻找父节点
                    CourseType parentCourseType = map.get(courseType.getPid().longValue());
                    parentCourseType.getChildren().add(courseType);
                }
            }
        }

        return JSONResult.success(firstNodeList);
    }

    /**
     * 查询课程类型面包屑数据
     */
    @Override
    public JSONResult crumbs(Long courseTypeId) {
        //courseTypeId=1040
        List<MbxDTO> returnList = new ArrayList<>();
        //1、入参校验

        /**
         * 1、入参校验
         * 2、根据入参ID查询课程类型详情，courseType（java基础，pid:1039）
         * 3、从courseType中获取path字段的值（1037.1039.1040）
         * 4、根据.进行切割，得到数组（1037、1039、1040）
         * 5、根据每个ID查询本身
         * 6、再根据PID查询兄弟节点
         * 7、将数据添加到返回结果集合中
         */
        //2、根据入参ID查询课程类型详情，courseType（java基础，pid:1039）
        CourseType courseType = courseTypeMapper.selectById(courseTypeId);

        //3、从courseType中获取path字段的值（1037.1039.1040）
        String path = courseType.getPath();

        //4、根据.进行切割，得到数组（1037、1039、1040）
        String[] split = path.split("\\.");

        //5、根据每个ID查询本身
        List<CourseType> courseTypeList = courseTypeMapper.selectBatchIds(Arrays.asList(split));

        MbxDTO dto = null;
        //6、再根据PID查询兄弟节点
        for(int i=0; i<courseTypeList.size(); i++){
            dto = new MbxDTO();
            //1040、1039
            CourseType temp = courseTypeList.get(i);

            //查询当前节点的所有兄弟节点（根据PID查询：拥有相同pid的节点就是兄弟节点）
            //下面三行代码相当于我们手写的SQL：select * from t_course_type where pid = 1039 and id != 1040
            Wrapper<CourseType> wrapper = new EntityWrapper();
            wrapper.eq("pid", temp.getPid());
            wrapper.ne("id", temp.getId());
            List<CourseType> list = courseTypeMapper.selectList(wrapper);

            dto.setOwnerCourseType(temp);
            dto.setOtherCourseTypes(list);

            //封装结果
            returnList.add(dto);
        }
        return JSONResult.success(returnList);
    }

    //用redisTemplate做的缓存
    public JSONResult treeData3() {
        /**
         * 1、查询缓存，看是否存在课程分类数据
         * 2、如果存在，直接返回数据，不需要查询数据库
         * 3、如果不存在，查询数据库，并将数据存到缓存
         * 4、数据返回前端
         */
        //1、查询缓存，看是否存在课程分类数据
        List<CourseType> listFromRedis = (List<CourseType>)redisTemplate.opsForValue().get(BaseConstants.RedisConstants.COURSE_TYPE);
        if(!CollectionUtils.isEmpty(listFromRedis)){
            log.info("从Redis获取到了数据");
            //缓存中有数据
            return JSONResult.success(listFromRedis);
        }else{
            log.info("从Redis没有获取到数据，查数据库");
            //缓存中没有数据，查询数据库，并将数据存到缓存
            //查询所有课程分类数据
            List<CourseType> courseTypeList = courseTypeMapper.selectList(null);

            List<CourseType> firstNodeList = new ArrayList<>();//只存放一级节点

            Map<Long, CourseType> map = new HashMap<>();
            for (int k = 0; k < courseTypeList.size(); k++) {
                CourseType courseType = courseTypeList.get(k);
                map.put(courseType.getId(), courseType);
            }

            if(!CollectionUtils.isEmpty(courseTypeList)){
                for (int i = 0; i < courseTypeList.size(); i++) {
                    CourseType courseType = courseTypeList.get(i);//pid:1037
                    if(courseType.getPid().longValue() == 0){
                        //表示当前节点为第一级节点
                        firstNodeList.add(courseType);
                    }
                    else{
                        //说明当前节点非一级
                        //寻找父节点
                        CourseType parentCourseType = map.get(courseType.getPid().longValue());
                        parentCourseType.getChildren().add(courseType);
                    }
                }
            }

            //数据存缓存
            redisTemplate.opsForValue().set(BaseConstants.RedisConstants.COURSE_TYPE, firstNodeList);
            return JSONResult.success(firstNodeList);
        }
    }


    public JSONResult treeData2() {
        //查询所有课程分类数据
        List<CourseType> courseTypeList = courseTypeMapper.selectList(null);

        List<CourseType> firstNodeList = new ArrayList<>();//只存放一级节点

        if(!CollectionUtils.isEmpty(courseTypeList)){
            for (int i = 0; i < courseTypeList.size(); i++) {
                CourseType courseType = courseTypeList.get(i);//pid:1037
                if(courseType.getPid().longValue() == 0){
                    //表示当前节点为第一级节点
                    firstNodeList.add(courseType);
                }
                else{
                    //说明当前节点非一级
                    for (int k = 0; k < courseTypeList.size(); k++) {
                        CourseType parentCourseType = courseTypeList.get(k);
                        //寻找父节点
                        if(courseType.getPid().longValue() == parentCourseType.getId().longValue()){
                            parentCourseType.getChildren().add(courseType);
                            break;
                        }
                    }
                }
            }
        }
        return JSONResult.success(firstNodeList);
    }
}
