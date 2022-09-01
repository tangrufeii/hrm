package cn.itsource.dto;

import lombok.Data;

/**
 * 课程查询对象
 */
@Data
public class CourseQueryDto {

    private Long courseTypeId;//课程分类
    private String gradeName;//等级名称
    private String keyword;//关键字搜索
    private Integer page = 1;//第几页
    private Integer rows = 10;//每页条数
    private Double priceMax;//最大价格
    private Double priceMin;//最小价格
    private String sortField;//排序字段
    private String sortType;//排序类型
    private String tenantName;//机构名称
}
