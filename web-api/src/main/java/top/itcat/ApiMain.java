package top.itcat;

import org.apache.zookeeper.KeeperException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import top.itcat.cache.CacheService;

import java.io.IOException;

@EnableCaching
@EnableRedisHttpSession
@SpringBootApplication
//@EnableJms
@EnableWebMvc
@ServletComponentScan(basePackages = {"top.itcat.filter", "top.itcat.listener"})
public class ApiMain {
    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        ConfigurableApplicationContext ctx = SpringApplication.run(ApiMain.class, args);
        CacheService cacheService = (CacheService) ctx.getBean("cacheService");
        RedisTemplate redis = (RedisTemplate) ctx.getBean("redisTemplate");
        cacheService.getCacheManager().withRedis(redis);
    }
}