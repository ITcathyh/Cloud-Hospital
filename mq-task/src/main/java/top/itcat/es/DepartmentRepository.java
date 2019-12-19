package top.itcat.es;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;
import top.itcat.entity.Department;

@Component
public interface DepartmentRepository extends ElasticsearchRepository<Department, Long> {
}
