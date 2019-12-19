package top.itcat.aop.auth;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import top.itcat.aop.BaseAopService;
import top.itcat.aop.auth.annotation.RoleCheck;
import top.itcat.bean.user.ApiUser;
import top.itcat.rpc.service.model.RoleEnum;
import top.itcat.util.GetBaseResponUtil;
import top.itcat.util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;

/**
 * 权限验证模块
 *
 * @author huangyuhang
 */
@Aspect
@Component
@Slf4j
public class AuthService extends BaseAopService {
    @Pointcut("@within(top.itcat.aop.auth.annotation.RoleCheck) || @annotation(top.itcat.aop.auth.annotation.RoleCheck)")
    public void roleCheckService() {
    }

    @Around("roleCheckService()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Method method = getMethod(point);
        RoleCheck roleCheck = method.getAnnotation(RoleCheck.class);

        if (roleCheck == null) {
            roleCheck = point.getTarget().getClass().getAnnotation(RoleCheck.class);
        }

        HttpServletRequest request = (HttpServletRequest) RequestContextHolder.getRequestAttributes().
                resolveReference(RequestAttributes.REFERENCE_REQUEST);
        Claims claims = JwtUtil.parseJWT(request.getHeader("token"));

        if (claims == null && roleCheck.needLogin()) {
            return GetBaseResponUtil.getBaseRspStr(401, "Token解析错误");
        }

        if (claims != null) {
            request.setAttribute("claims", claims);
        }

        if (!roleCheck.needLogin()) {
            return point.proceed();
        }

        if (request.getRequestURI().startsWith("/file")) {
            return point.proceed();
        }

        HttpSession session = request.getSession();
        Object obj = session.getAttribute("user");

        if (obj == null) {
            return GetBaseResponUtil.getBaseRspStr(401, "Session失效");
        }

//        if (Long.valueOf((String) ((Claims) request.getAttribute("claims")).get("id")) != ((ApiUser) obj).getId()) {
//            log.warn("invalid request,param:{}", request.getParameterMap());
//            return GetBaseResponUtil.getBaseRspStr(401, "未登录");
//        }

        if (roleCheck.roles().length == 0) {
            return point.proceed();
        }

        for (RoleEnum needRole : roleCheck.roles()) {
            if (needRole.equals(((ApiUser) obj).getRole())) {
                return point.proceed();
            }
        }

        return GetBaseResponUtil.getBaseRspStr(403, "无操作权限");
    }
}
