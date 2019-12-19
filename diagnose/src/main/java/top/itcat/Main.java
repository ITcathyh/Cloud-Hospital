package top.itcat;

import org.apache.zookeeper.KeeperException;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import top.itcat.cache.CacheService;
import top.itcat.cache.RedisCache;
import top.itcat.concurrent.CommonThreadPoolFactory;
import top.itcat.rpc.service.diagnose.DiagnoseService;
import top.itcat.rpc.thrift.ThriftUtil;
import top.itcat.rpc.zookeeper.ZookeeperUtil;
import top.itcat.task.ScheduleTaskCentry;
import top.itcat.util.IPUtil;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;

@SpringBootApplication
@EnableCaching
@EnableAsync
@MapperScan("top.itcat.mapper")
@EnableTransactionManagement(proxyTargetClass = true)
@SuppressWarnings("unchecked")
public class Main {
    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext ctx = SpringApplication.run(Main.class, args);
        CacheService cacheService = (CacheService) ctx.getBean("cacheService");
        RedisTemplate<String, Object> redis = (RedisTemplate) ctx.getBean("redisTemplate");
        cacheService.getCacheManager().withRedis(redis);
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
                ZookeeperUtil.register("/" + DiagnoseService.class.getSimpleName(), String.format("/%s:", ip),
                        port, null);
            } catch (Exception e) {
                System.exit(-1);
            }

            ((ScheduleTaskCentry)ctx.getBean("scheduleTaskCentry")).generateRedisRemain();
        }).start();

        DiagnoseService.Iface service = (DiagnoseService.Iface) ctx.getBean("serviceServer");
        ThriftUtil.beginService(new DiagnoseService.Processor<>(service), port, CommonThreadPoolFactory.newThreadPool(6), 2);
    }
}