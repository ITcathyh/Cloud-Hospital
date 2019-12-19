package top.itcat.aop.limiter.annotation;

import java.lang.annotation.*;

@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimit {
    int limitNum() default 100;

    String[] fieldKeys() default {};
}
