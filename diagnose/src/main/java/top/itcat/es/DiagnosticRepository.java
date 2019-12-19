package top.itcat.es;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;
import top.itcat.entity.diagnostic.Diagnostic;

@Component
public interface DiagnosticRepository extends ElasticsearchRepository<Diagnostic, Long> {
}
