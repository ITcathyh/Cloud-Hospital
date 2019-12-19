package top.itcat.aop.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import top.itcat.bean.user.ApiUser;
import top.itcat.cache.manage.DefaultCacheManager;
import top.itcat.exception.CommonException;
import top.itcat.exception.ExcessiveAttemptsException;
import top.itcat.exception.InvalidParamException;
import top.itcat.exception.UnknownUserException;
import top.itcat.rpc.client.UserServiceClient;
import top.itcat.rpc.service.model.PackedUser;
import top.itcat.rpc.service.model.RoleEnum;
import top.itcat.util.JwtUtil;

import javax.servlet.http.HttpSession;
import java.util.concurrent.TimeUnit;

/**
 * 权限管理模块
 * 包含登陆验证、登陆安全确认
 *
 * @author huangyuhang
 */
@Component
@Slf4j
public class AuthManager {
    private final RedisTemplate redisTemplate;
    private final UserServiceClient userService;
    private static final short MAX_LOGIN_TIME = 5;
    private final DefaultCacheManager cache;

    @Autowired
    public AuthManager(UserServiceClient userService, RedisTemplate redisTemplate, DefaultCacheManager cache) {
        this.userService = userService;
        this.redisTemplate = redisTemplate;
        this.cache = cache;
    }

    @SuppressWarnings("unchecked")
    public String doLogin(HttpSession session,
                          String username, String password) throws CommonException {
        if (StringUtils.isEmpty(username) ||
                StringUtils.isEmpty(password) ||
                session == null) {
            throw new InvalidParamException();
        }

//        if (session.getAttribute("user") != null) {
//            return null;
//        }

        // 验证错误登陆次数
        String lockKey = "lock:" + username;

        if (cache.get(lockKey) != null) {
            throw new ExcessiveAttemptsException();
        }

        String key = "retry:" + username;
        Long retryCount = redisTemplate.opsForValue().increment(key, 1);

        if (retryCount != null) {
            if (retryCount == 1) {
                for (int i = 0; i < 2 && !redisTemplate.expire(key, 15, TimeUnit.SECONDS); ++i) {
                    log.error("expire err, key({}))", key);
                }
            } else if (retryCount >= MAX_LOGIN_TIME) {
                cache.set(lockKey, 1, 15, 10);
                throw new ExcessiveAttemptsException();
            }
        }

        // 调用统一服务登陆
        PackedUser user = userService.getUserByUsernameAndPassword(username, password, RoleEnum.Doctor);

        if (user == null) {
            throw new UnknownUserException();
        }

        ApiUser apiUser = new ApiUser(user);

        redisTemplate.delete(key);
        cache.del(lockKey);
        session.setAttribute("user", apiUser);

        return JwtUtil.createJWT(TimeUnit.MINUTES.toMillis(150000), apiUser);
    }

    public void doLogout(HttpSession session) throws CommonException {
        session.removeAttribute("user");
    }
}
