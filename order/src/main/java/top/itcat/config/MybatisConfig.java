package top.itcat.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.baomidou.mybatisplus.MybatisConfiguration;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.plugins.PerformanceInterceptor;
import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import top.itcat.aop.multipledb.DataSourceContextHolder;
import top.itcat.aop.multipledb.DataSourceType;
import top.itcat.aop.multipledb.MultipleDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class MybatisConfig {
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    @Bean
    public PerformanceInterceptor performanceInterceptor() {
        return new PerformanceInterceptor();
    }

    @Bean(name = "master")
    @ConfigurationProperties(prefix = "spring.datasource.druid.master")
    public DataSource master() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "slave1")
    @ConfigurationProperties(prefix = "spring.datasource.druid.slave1")
    public DataSource slave1() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @Primary
    public DataSource multipleDataSource(@Qualifier("master") DataSource master,
                                         @Qualifier("slave1") DataSource slave1) {
        MultipleDataSource multipleDataSource = new MultipleDataSource();
        Map<Object, Object> targetDataSources = new HashMap<>(2);
        targetDataSources.put("master", master);
        targetDataSources.put("slave1", slave1);
        DataSourceContextHolder.addSource(DataSourceType.SLAVE, "slave1");
        DataSourceContextHolder.addSource(DataSourceType.MASTER, "master");

        multipleDataSource.setTargetDataSources(targetDataSources);
        multipleDataSource.setDefaultTargetDataSource(master);
        return multipleDataSource;
    }

    @Bean("sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(multipleDataSource(master(), slave1()));

        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setCacheEnabled(true);
        sqlSessionFactory.setConfiguration(configuration);
        sqlSessionFactory.setPlugins(new Interceptor[]{
                paginationInterceptor()
        });
        return sqlSessionFactory.getObject();
    }
}
