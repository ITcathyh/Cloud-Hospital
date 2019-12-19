package top.itcat.cache;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;
import top.itcat.concurrent.CommonThreadPoolFactory;

import java.util.Random;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Slf4j
public class RedisCache implements Cache {
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private volatile static RedisTemplate<String, Object> redisTemplate = null;
    private final String id;
    private Random random = new Random();
    private int expireTime = 600;
    private ExecutorService threadPool = CommonThreadPoolFactory.newThreadPool();

    public RedisCache(String id) {
        if (id == null) {
            throw new IllegalArgumentException("Cache instances require an ID");
        }
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void putObject(Object key, Object value) {
        if (value != null) {
            redisTemplate.opsForValue().set(key.toString(), value, expireTime + random.nextInt(100), TimeUnit.SECONDS);
        }
    }

    public Object getObject(Object key) {
        try {
            if (key != null) {
                return redisTemplate.opsForValue().get(key.toString());
            }
        } catch (Exception e) {
            log.error("redis", e);
        }
        return null;
    }

    public Object removeObject(Object key) {
        try {
            if (key != null) {
                redisTemplate.delete(key.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void clear() {
        log.debug("清空缓存:" + this.id);
        threadPool.execute(() -> {
            try {
                Set<String> keys = redisTemplate.keys("*" + this.id + "*");
                if (!CollectionUtils.isEmpty(keys)) {
                    redisTemplate.delete(keys);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                log.debug("清空缓存完成:");
            }

        });
//        try {
////                ((Jedis) redisTemplate.getConnectionFactory().
////                        getConnection().getNativeConnection()).eval("local ks = redis.call('KEYS', KEYS [1])  \n" +
////                        "for i=1,#ks,5000 do \n" +
////                        "    redis.call('del', unpack(ks, i, math.min(i+4999, #ks)))\n" +
////                        " end\n" +
////                        "return true", 1, "*" + this.id + "*");
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }

    public int getSize() {
        Long size = redisTemplate.execute(RedisServerCommands::dbSize);

        /*
               Long size = (Long) getRedisTemplate().execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.dbSize();
            }
        });
         */

        return size == null ? 0 : size.intValue();
    }

    public ReadWriteLock getReadWriteLock() {
        return this.readWriteLock;
    }

    public static void setRedisTemplate(RedisTemplate<String, Object> redis) {
        redisTemplate = redis;
    }
}
