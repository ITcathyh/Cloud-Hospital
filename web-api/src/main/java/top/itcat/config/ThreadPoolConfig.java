package top.itcat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import top.itcat.concurrent.CommonThreadPoolFactory;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class ThreadPoolConfig {
    @Bean
    public Executor myExecutor() {
        return CommonThreadPoolFactory.newThreadPool();
    }
}
