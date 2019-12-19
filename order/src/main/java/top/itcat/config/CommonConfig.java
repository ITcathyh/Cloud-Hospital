package top.itcat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.itcat.aop.multipledb.DataSourceService;
import top.itcat.cache.CacheService;

@Configuration
public class CommonConfig {
    @Bean
    public DataSourceService dataSourceService() {
        return new DataSourceService();
    }

    @Bean
    public CacheService cacheService() {
        return new CacheService();
    }
}
