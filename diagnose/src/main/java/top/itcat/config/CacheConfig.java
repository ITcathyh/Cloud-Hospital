package top.itcat.config;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.config.CacheConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import top.itcat.cache.CacheService;
import top.itcat.cache.FastJson2JsonRedisSerializer;
import top.itcat.cache.annotation.Const;
import top.itcat.cache.manage.DefaultCacheManager;

@Configuration
public class CacheConfig {
    @Bean
    public RedisSerializer redisSerializer() {
        return new FastJson2JsonRedisSerializer<Object>(Object.class);
    }

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) throws Exception {
        RedisTemplate redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setValueSerializer(redisSerializer());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setDefaultSerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(redisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    public DefaultCacheManager defaultCacheManager() {
        return new DefaultCacheManager<>("projectDefaultCacheManager");
    }

    @Bean
    public Ehcache ehcache() {
        net.sf.ehcache.CacheManager manager = net.sf.ehcache.CacheManager.getInstance();
        Ehcache cache = manager.addCacheIfAbsent("ehcache");
        CacheConfiguration config = cache.getCacheConfiguration();

        config.setTimeToIdleSeconds(Const.DEFAULT_LOCAL_EXPIRE_TIME);
        config.setTimeToLiveSeconds(Const.DEFAULT_LOCAL_EXPIRE_TIME << 2);
        config.setMaxEntriesLocalHeap(1000);
        config.setMaxEntriesLocalDisk(100000);
        config.setEternal(false);
        config.setMaxEntriesInCache(10000);
        config.setDiskExpiryThreadIntervalSeconds(120);
        config.memoryStoreEvictionPolicy("LRU");
        return cache;
    }
}
