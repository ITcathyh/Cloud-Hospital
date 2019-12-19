package top.itcat.aop.multipledb.annotation;

import top.itcat.aop.multipledb.DataSourceType;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DataSource {
    DataSourceType type() default DataSourceType.MASTER;

    String name() default "";
}