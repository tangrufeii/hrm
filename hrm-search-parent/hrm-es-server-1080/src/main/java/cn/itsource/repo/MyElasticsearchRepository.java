package cn.itsource.repo;

import cn.itsource.doc.CourseDoc;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

@Component
public interface MyElasticsearchRepository extends ElasticsearchRepository<CourseDoc, Long> {
}