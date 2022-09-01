package cn.itsource.doc;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@Data
@Document(indexName= "hrm", type = "course")
public class CourseDoc {

    //标记为文档ID
    @Id
    private Long id;

    /**
     * Text和Keyword的区别：
     * 两者都会建立倒排索引
     * 但是text会对字符串先进行分词，然后再建立倒排索引，但是Keyword不会分词，直接建立倒排索引
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String name;

    /**
     * 适用人群
     */
    @Field(type = FieldType.Keyword)
    private String forUser;

    /**
     * 课程分类
     */
    @Field(type = FieldType.Long)
    private Long courseTypeId;

    /**
     * 机构名称
     */
    //@Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    @Field(type = FieldType.Keyword)
    private String tenantName;

    //课程等级
    @Field(type = FieldType.Keyword)
    private String gradeName;

    /**
     * 封面
     */
    @Field(type = FieldType.Keyword, index = false)//封面不参与搜索，所以不需要建立倒排索引文档
    private String pic;

    /**
     * 销售量
     */
    @Field(type = FieldType.Integer)
    private Integer saleCount;

    /**
     * 浏览量
     */
    @Field(type = FieldType.Integer)
    private Integer viewCount;

    /**
     * 评论数
     */
    @Field(type = FieldType.Integer)
    private Integer commentCount;

    /**
     * 上线时间
     */
    @Field(type = FieldType.Date)
    private Date onlineTime;

    /**
     * 收费规则：，收费1免费，2收费
     */
    @Field(type = FieldType.Integer)
    private Integer charge;

    //课程价格
    @Field(type = FieldType.Double)
    private Float price;

    /**
     * 原价
     */
    @Field(type = FieldType.Double)
    private Float priceOld;
}
