package cn.itsource.service.impl;

import cn.itsource.doc.CourseDoc;
import cn.itsource.dto.CourseQueryDto;
import cn.itsource.mapper.HighlightResultMapper;
import cn.itsource.repo.MyElasticsearchRepository;
import cn.itsource.result.PageList;
import cn.itsource.service.EsService;
import cn.itsource.vo.AggTermsVo;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class EsServiceImpl implements EsService {

    @Autowired
    private MyElasticsearchRepository repo;

    @Autowired
    private HighlightResultMapper highlightResultMapper;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;


    /**
     * 课程搜索，不从数据库查询，而是从ES中搜索数据
     * 1、怎么搜索，API？
     * 2、ES中是否有数据
     */
    @Override
    public PageList<CourseDoc> searchCourse(CourseQueryDto dto) {
        //本地查询构建器
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

        //排序
        if(StringUtils.hasLength(dto.getSortField())){
            SortOrder order = SortOrder.DESC;
            if("asc".equals(dto.getSortType())){
                order = SortOrder.ASC;
            }
            queryBuilder.withSort(SortBuilders.fieldSort(dto.getSortField()).order(order));
        }

        //分页
        queryBuilder.withPageable(PageRequest.of(dto.getPage() - 1, dto.getRows()));


        //获取组合查询器
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        /**
         * 组合查询器做：查询和过滤
         */
        //过滤
        if(dto.getCourseTypeId() != null){
            boolQueryBuilder.filter(QueryBuilders.termQuery("courseTypeId", dto.getCourseTypeId()));
        }
        //比较原则：小于等于最大值，大于等于最小值
        if(dto.getPriceMax() != null){
            boolQueryBuilder.filter(QueryBuilders.rangeQuery("price").lte(dto.getPriceMax()));
        }
        if(dto.getPriceMin() != null){
            boolQueryBuilder.filter(QueryBuilders.rangeQuery("price").gte(dto.getPriceMin()));
        }

        if(StringUtils.hasLength(dto.getGradeName())){
            //查询等级
            boolQueryBuilder.filter(QueryBuilders.termQuery("gradeName", dto.getGradeName()));
        }
        if(StringUtils.hasLength(dto.getTenantName())){
            //查询机构名
            boolQueryBuilder.filter(QueryBuilders.termQuery("tenantName", dto.getTenantName()));
        }

        /**
         * termQuery:不分词查询
         * matchQuery:分词查询
         */

        //查询
        if(StringUtils.hasLength(dto.getKeyword())){
            boolQueryBuilder.must(QueryBuilders.matchQuery("name", dto.getKeyword()));
        }

        queryBuilder.withQuery(boolQueryBuilder);//这里传入的参数，就是封装我们的查询条件


        //关键字高亮
        HighlightBuilder.Field field1 = new HighlightBuilder.Field("name").preTags("<font style='color:red'><b>").postTags("</b></font>");
        HighlightBuilder.Field field2 = new HighlightBuilder.Field("tenantName").preTags("<font style='color:red'><b>").postTags("</b></font>");
        queryBuilder.withHighlightFields(field1, field2);

        //聚合查询
        TermsAggregationBuilder termsAggregationBuilder1 = AggregationBuilders.terms("gradeAggs").field("gradeName").order(Terms.Order.count(false)).size(10);
        TermsAggregationBuilder termsAggregationBuilder2 = AggregationBuilders.terms("tenantAggs").field("tenantName").order(Terms.Order.count(false)).size(10);
        queryBuilder.addAggregation(termsAggregationBuilder1).addAggregation(termsAggregationBuilder2);

        //执行查询
        AggregatedPage<CourseDoc> page = elasticsearchTemplate.queryForPage(queryBuilder.build(), CourseDoc.class, highlightResultMapper);

        Map<String, List<AggTermsVo>> aggrMap = new HashMap<>();

        Map<String, Aggregation> stringAggregationMap = page.getAggregations().asMap();
        Set<Map.Entry<String, Aggregation>> entries = stringAggregationMap.entrySet();
        entries.forEach(e -> {
            List<AggTermsVo> list = new ArrayList<>();
            String key = e.getKey();
            StringTerms value = (StringTerms) e.getValue();
            //取出聚合计算结果
            List<StringTerms.Bucket> buckets = value.getBuckets();
            AggTermsVo dto2 = null;
            for(int i = 0; i<buckets.size(); i++){
                dto2 = new AggTermsVo();
                StringTerms.Bucket bucket = buckets.get(i);
                String keyAsString = bucket.getKeyAsString();
                long docCount = bucket.getDocCount();

                dto2.setKey(keyAsString);
                dto2.setDocCount(docCount);
                list.add(dto2);
            }
            aggrMap.put(key, list);
        });
        //将结果封装返回
        return new PageList<CourseDoc>(page.getTotalElements(), page.getContent(), aggrMap);
    }
}
