package top.itcat;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.KeeperException;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import top.itcat.cache.RedisCache;
import top.itcat.rpc.service.user.UserService;
import top.itcat.rpc.thrift.ThriftUtil;
import top.itcat.rpc.zookeeper.ZookeeperUtil;
import top.itcat.util.IPUtil;

import java.io.IOException;

@SpringBootApplication
@EnableCaching
@EnableAsync
@MapperScan("top.itcat.mapper")
@EnableTransactionManagement(proxyTargetClass = true)
@SuppressWarnings("unchecked")
@Slf4j
public class Main {
    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        ConfigurableApplicationContext ctx = SpringApplication.run(Main.class, args);
//        CacheService cacheService = (CacheService) ctx.getBean("cacheService");
        RedisTemplate<String, Object> redis = (RedisTemplate) ctx.getBean("redisTemplate");
//        cacheService.getCacheManager().withRedis(redis);
        RedisCache.setRedisTemplate(redis);

        int defaultPort = ctx.getEnvironment().getProperty("service.port", Integer.class);
        int port = IPUtil.getOpenPort(args, defaultPort);
        String ip = IPUtil.getLocalIP();

        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                ZookeeperUtil.register("/" + UserService.class.getSimpleName(), String.format("/%s:", ip),
                        port, null);
            } catch (Exception e) {
                System.exit(-1);
            }
        }).start();

        UserService.Iface service = (UserService.Iface) ctx.getBean("serviceServer");
        ThriftUtil.beginService(new UserService.Processor<>(service), port);
    }
}