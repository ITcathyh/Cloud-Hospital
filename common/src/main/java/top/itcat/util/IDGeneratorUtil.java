package top.itcat.util;

import org.joda.time.DateTime;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.Jedis;
import top.itcat.exception.InvalidParamException;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import static top.itcat.constant.RedisKey.GET_INCRING_SEQ;

/**
 * ID生成工具
 *
 * @author huangyuhang
 */
public class IDGeneratorUtil {
    private static final char[] CHARS = {'0', '1', '2', '3', '4', '5', '6', '7', '8',
            '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M',
            'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    private static Random random = new Random(System.currentTimeMillis());

    /**
     * 获取随机字符串
     *
     * @param len 长度
     * @return String
     */
    public static String getRandomString(int len) {
        if (len <= 0) {
            return "";
        }

        StringBuilder stra = new StringBuilder("");

        for (int i = 0; i < len; i++) {
            stra.append(CHARS[random.nextInt(CHARS.length)]);
        }

        return stra.toString();
    }

    /**
     * 获取随机字符串（仅由数组组成)
     *
     * @param len 长度
     * @return String
     */
    public static String getRandomNumStr(int len) {
        if (len <= 0) {
            return "";
        }

        StringBuilder stra = new StringBuilder("");

        for (int i = 0; i < len; i++) {
            stra.append(CHARS[random.nextInt(10)]);
        }

        return stra.toString();
    }

    /**
     * 根据时间、特征key、长度和随机字符生成ID
     *
     * @param len 长度
     * @param key 特征key
     * @return String
     */
    public static String genID(int len, String key) {
        StringBuilder stra = new StringBuilder("");
        DateTime dateTime = new DateTime();

        stra.append(dateTime.toString("yyyyMMddHHmmss"));
//        stra.append(dateTime.toString("sss"));
        stra.append(key);
        stra.append(getRandomString(len - stra.length()));

        return stra.toString();
    }

    /**
     * 根据时间、特征key、长度和随机字符生成纯数字ID
     *
     * @param len  长度
     * @param keys 特征key
     * @return String
     */
    public static String genOnlyNumID(int len, String... keys) {
        StringBuilder stra = new StringBuilder("");
        DateTime dateTime = new DateTime();

        stra.append(dateTime.toString("yyyyMMddHHmmss"));
        stra.append(dateTime.toString("sss"));

        for (String key : keys) {
            stra.append(key);
        }

        stra.append(getRandomNumStr(len - stra.length()));

        return stra.toString();
    }

    /**
     * 根据时间和特征ID生成主键
     *
     * @param num 特征ID
     * @return Long
     */
    public static Long genPrimaryID(Number num) {
        long lkey;

        try {
            StringBuilder keyBuilder = new StringBuilder(String.valueOf(num));

            while (keyBuilder.length() < 3) {
                keyBuilder.insert(0, "0");
            }

            String key = keyBuilder.toString();
            lkey = Long.valueOf(key.substring(key.length() - 3));
        } catch (NumberFormatException e) {
            return null;
        }

        String stra = Long.toString(System.currentTimeMillis()) +
                lkey +
                getRandomNumStr(1);
        return Long.valueOf(stra);
    }

    /**
     * 根据时间、特征ID和redis自增生成主键
     * len >= 10
     *
     * @param num 特征ID
     * @return Long
     */
    public static Long genPrimaryID(Number num,
                                    RedisTemplate<String, Object> redisTemplate,
                                    String redisKey) {
        StringBuilder stra = new StringBuilder();
        DateTime dateTime = new DateTime();
        stra.append(dateTime.toString("yyMMdd"));

        try {


//            long curNUm = (long) ((Jedis) redisTemplate
//                    .getConnectionFactory()
//                    .getConnection()
//                    .getNativeConnection())
//                    .eval(GET_INCRING_SEQ,
//                            Collections.singletonList(redisKey),
//                            Collections.singletonList(Long.toString(expireTime)));

            long curNum = redisTemplate.opsForValue().increment(redisKey, 1);

            if (curNum == 1) {
                DateTime nextDay = new DateTime().plusDays(1).withMillisOfDay(0);
                long expireTime = (nextDay.getMillis() - dateTime.getMillis()) / 1000;
                redisTemplate.expire(redisKey, expireTime, TimeUnit.SECONDS);
            }

            // 暂时去除
//            long curNum = (long) ((Jedis) redisTemplate
//                    .getConnectionFactory()
//                    .getConnection()
//                    .getNativeConnection())
//                    .eval(GET_INCRING_SEQ, 2, redisKey, String.valueOf(expireTime));

            StringBuilder keyBuilder = new StringBuilder(String.valueOf(curNum));

            while (keyBuilder.length() < 4) {
                keyBuilder.insert(0, "0");
            }

            stra.append(keyBuilder);
        } catch (Exception e) {
            return null;
        }

        if (num != null) {
            stra.append(num);
        }

        return Long.valueOf(stra.toString());
    }

    /**
     * 根据特征Key和redis自增生成Code
     * len(Key) = 3
     * len = 8
     *
     * @param key 特征Key
     * @return String
     */
    public static String genCode(String key,
                                 RedisTemplate<String, Object> redisTemplate,
                                 String redisKey) {
        if (key == null || key.length() != 3) {
            throw new InvalidParamException();
        }

        Long cur = redisTemplate.opsForValue().increment(redisKey, 1);

        if (cur == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder(key);
        StringBuilder numsb = new StringBuilder(String.valueOf(cur));

        while (numsb.length() < 5) {
            numsb.insert(0, "0");
        }

        sb.append(numsb);
        return sb.toString();
    }
}
