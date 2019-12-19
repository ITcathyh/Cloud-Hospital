package top.itcat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.itcat.aop.multipledb.DataSourceService;

@Configuration
public class CommonConfig {
    @Bean
    public DataSourceService dataSourceService() {
        return new DataSourceService();
    }
}
