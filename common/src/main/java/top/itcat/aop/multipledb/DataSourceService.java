package top.itcat.aop.multipledb;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import top.itcat.aop.BaseAopService;
import top.itcat.aop.multipledb.annotation.DataSource;

import java.lang.reflect.Method;

@Component
@Aspect
@Order(-1)
public class DataSourceService extends BaseAopService {
    @Pointcut("@within(top.itcat.aop.multipledb.annotation.DataSource) || @annotation(top.itcat.aop.multipledb.annotation.DataSource) ")
    public void pointCut() {

    }

    @Around(value = "pointCut()")
    public Object dealDataSourceService(ProceedingJoinPoint point) throws Throwable {
        Method method = getMethod(point);
        DataSource dataSource = method.getAnnotation(DataSource.class);

        if (dataSource == null) {
            dataSource = point.getTarget().getClass().getAnnotation(DataSource.class);
        }

        try {
            if (StringUtils.isEmpty(dataSource.name())) {
                try {
                    if (method.getName().startsWith("select")) {
                        DataSourceContextHolder.setDataSource(DataSourceType.SLAVE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (DataSourceContextHolder.getDataSource() == null) {
                if (!StringUtils.isEmpty(dataSource.name())) {
                    DataSourceContextHolder.setDataSource(dataSource.name());
                } else {
                    DataSourceContextHolder.setDataSource(dataSource.type());
                }
            }

            return point.proceed();
        } finally {
            DataSourceContextHolder.clear();
        }
    }
}
