package top.itcat.aop.limiter;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import top.itcat.aop.BaseAopService;
import top.itcat.aop.limiter.annotation.RateLimit;
import top.itcat.exception.RateLimitException;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Aspect
@SuppressWarnings("all")
@Slf4j
public class LimitService extends BaseAopService {
    protected final Map<String, RateLimiter> map = new ConcurrentHashMap<>();

    @Pointcut("@annotation(top.itcat.aop.limiter.annotation.RateLimit)")
    public void limitService() {
    }

    @Around("limitService()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object obj = null;
        Method method = getMethod(point);
        RateLimit rateLimit = method.getAnnotation(RateLimit.class);
        double limitNum = rateLimit.limitNum();
        StringBuilder fieldKey = new StringBuilder("");
        String[] fieldKeys = rateLimit.fieldKeys();

        if (fieldKeys.length != 0) {
            for (String key : fieldKeys) {
                fieldKey.append(parseKey(key, method, point.getArgs())).append(":");
            }
        }

        String interfaceName = getInterfaceName(point.getTarget().getClass().getSimpleName(),
                method.getName(), fieldKey.toString());
        RateLimiter rateLimiter;

        if (map.containsKey(interfaceName)) {
            rateLimiter = map.get(interfaceName);
        } else {
            rateLimiter = map.get(interfaceName);

            if (rateLimiter == null) {
                rateLimiter = RateLimiter.create(limitNum);
                map.put(interfaceName, rateLimiter);
            }
        }

        if (rateLimiter.tryAcquire()) {
            obj = point.proceed();
        } else {
            log.info("接口限流：" + interfaceName);
            throw new RateLimitException();
        }

        return obj;
    }

    public RateLimiter setLimit(String className, String methodName, int limit) {
        return setLimit(className, methodName, "", limit);
    }

    public RateLimiter setLimit(String className, String methodName, String id, int limit) {
        String interfaceName = getInterfaceName(className, methodName, id);
        RateLimiter rateLimiter = map.get(interfaceName);

        if (rateLimiter == null) {
            map.put(interfaceName, RateLimiter.create(limit));
        }

        while (true) {
            rateLimiter = map.get(interfaceName);

            if ((int) rateLimiter.getRate() == limit) {
                break;
            }

            map.put(interfaceName, RateLimiter.create(limit));
        }

        rateLimiter.tryAcquire();
        return rateLimiter;
    }

    protected String getInterfaceName(String className, String methodName, String id) {
        String interfaceName = className + ":" + methodName;

        if (!StringUtils.isEmpty(id)) {
            interfaceName += ":" + id;
        }

        return interfaceName;
    }
}
