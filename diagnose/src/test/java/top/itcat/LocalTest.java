package top.itcat;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;
import top.itcat.entity.diagnostic.Diagnostic;
import top.itcat.es.DiagnosticRepository;
import top.itcat.service.DiagnosticService;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Main.class)
public class LocalTest {
    @Autowired
    private DiagnosticRepository diagnosticRepository;
    @Autowired
    private DiagnosticService diagnosticService;

    @Test
    public void testESSava() {
        List<Diagnostic> diagnosticList = diagnosticService.selectList(new EntityWrapper<Diagnostic>()
                .eq("valid", 1)
                .setSqlSelect("id,code,name,directory_id as directoryId"));
        diagnosticRepository.deleteAll();

        diagnosticRepository.save(diagnosticList);
    }

    @Test
    public void testESGet() {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery()
                .add(QueryBuilders.wildcardQuery("name", "*gd*"),
                        ScoreFunctionBuilders.weightFactorFunction(10))
                .add(QueryBuilders.wildcardQuery("code", "*gd*"),
                        ScoreFunctionBuilders.weightFactorFunction(10))
                .scoreMode("sum")
                .setMinScore(5);

//        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(0, 12);
        queryBuilder.withPageable(pageable)
                .withQuery(functionScoreQueryBuilder).build();
        Page<Diagnostic> page = diagnosticRepository.search(queryBuilder.build());
        System.out.println(page.getTotalElements());
        System.out.println(page.getTotalPages());

        for (Diagnostic h : page.getContent()) {
            System.out.println(h);
        }
    }
}
