package top.itcat.aop.processor;

import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.itcat.aop.limiter.LimitService;
import top.itcat.aop.limiter.annotation.RateLimit;

import java.lang.reflect.Method;

/**
 * 预处理注解
 * 接口限流手动热启动
 *
 * @author huangyuhang
 */
@Component
public class AnnotationScanner extends BaseAnnotationScanner {
    private final LimitService limitService;

    @Autowired
    public AnnotationScanner(LimitService limitService) {
        this.limitService = limitService;
    }

    @Override
    protected void process(Object bean, String beanName) {
        Class<?> aClass = AopUtils.getTargetClass(bean);
        Method[] methods = aClass.getMethods();

        for (Method method : methods) {
            RateLimit rateLimit = method.getAnnotation(RateLimit.class);

            // 包含fileKey的无法预处理
//            if (rateLimit != null && StringUtils.isEmpty(rateLimit.fieldKey())) {
//                limitService.setLimit(aClass.getSimpleName(), method.getName(), rateLimit.limitNum()).acquire(1);
//            }

            if (rateLimit != null && rateLimit.fieldKeys().length == 0) {
                limitService.setLimit(aClass.getSimpleName(), method.getName(), rateLimit.limitNum()).acquire(1);
            }
        }
    }
}
