package top.itcat.es;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;
import top.itcat.entity.Hosipital;

@Component
public interface HosipitalRepository extends ElasticsearchRepository<Hosipital, Long> {
}
