package cn.itsource.query;

import lombok.Data;

/**
 * @description:
 * @auth: tangrufei
 * @date: 2022-08-26 14:08
 */
@Data
public class BaseQuery {

    //关键字
    private String keyword;
    //有公共属性-分页
    private Integer page = 1; //当前页
    private Integer rows = 10; //每页显示多少条
    private Long[] ids; //存放批量操作时，前端传来的数据ID集合
}
