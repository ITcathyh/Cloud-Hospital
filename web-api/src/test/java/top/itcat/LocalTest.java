package top.itcat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LocalTest {
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Test
    public void test() {
        Set<String> keys = redisTemplate.keys("*top.itcat.mapper.NonmedicalChargeMapper*");

        System.out.println(keys.toString());

        if (!CollectionUtils.isEmpty(keys)) {
            redisTemplate.delete(keys);
        }
    }
}
