package top.itcat.aop.auth.annotation;

import org.springframework.core.annotation.Order;
import top.itcat.rpc.service.model.RoleEnum;

import java.lang.annotation.*;

@Inherited
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Order(1)
public @interface RoleCheck {
    boolean needLogin() default true;

    RoleEnum[] roles() default {};
}
