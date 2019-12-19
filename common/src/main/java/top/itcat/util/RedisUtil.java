package top.itcat.util;

import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import redis.clients.jedis.Protocol;
import redis.clients.util.SafeEncoder;

import java.io.Serializable;

public class RedisUtil {
//    private boolean setNx(StringRedisTemplate redis, String key, String value, long expiredTime) {
//        Boolean resultBoolean = null;
//        try {
//            resultBoolean = redis.execute((RedisCallback<Boolean>) connection -> {
//                Object nativeConnection = connection.getNativeConnection();
//                String redisResult = "";
//                @SuppressWarnings("unchecked")
//                RedisSerializer<String> stringRedisSerializer = (RedisSerializer<String>) redis.getKeySerializer();
//                //lettuce连接包下序列化键值，否知无法用默认的ByteArrayCodec解析
//                byte[] keyByte = stringRedisSerializer.serialize(key);
//                byte[] valueByte = stringRedisSerializer.serialize(value);
//                // lettuce连接包下 redis 单机模式setnx
//                if (nativeConnection instanceof RedisAsyncCommands) {
//                    RedisAsyncCommands commands = (RedisAsyncCommands)nativeConnection;
//                    //同步方法执行、setnx禁止异步
//                    redisResult = commands
//                            .getStatefulConnection()
//                            .sync()
//                            .set(keyByte, valueByte, SetArgs.Builder.nx().ex(10));
//                }
//                // lettuce连接包下 redis 集群模式setnx
//                if (nativeConnection instanceof RedisAdvancedClusterAsyncCommands) {
//                    RedisAdvancedClusterAsyncCommands clusterAsyncCommands = (RedisAdvancedClusterAsyncCommands) nativeConnection;
//                    redisResult = clusterAsyncCommands
//                            .getStatefulConnection()
//                            .sync()
//                            .set(keyByte, keyByte, SetArgs.Builder.nx().ex(10));
//                }
//                //返回加锁结果
//                return "OK".equalsIgnoreCase(redisResult);
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return resultBoolean != null && resultBoolean;
//    }

    public static boolean setnx(final RedisTemplate redisTemplate, final String key, final Serializable value, final long exptime) {
        return (Boolean) redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            RedisSerializer valueSerializer = redisTemplate.getValueSerializer();
            RedisSerializer keySerializer = redisTemplate.getKeySerializer();
            Object obj = connection.execute("set", keySerializer.serialize(key),
                    valueSerializer.serialize(value),
                    SafeEncoder.encode("NX"),
                    SafeEncoder.encode("EX"),
                    Protocol.toByteArray(exptime));
            return obj != null;
        });
    }
}
