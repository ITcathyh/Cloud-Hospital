package top.itcat.filter;


import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import top.itcat.util.JwtUtil;

import javax.servlet.http.HttpServletRequest;

@Configuration
@Slf4j
public class PreRequestSessionFilter extends ZuulFilter {
    @Autowired
    HttpServletRequest request;

    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 返回一个值来指定过滤器的执行顺序，不同过滤器允许返回相同的数字，数字越小顺序越靠前
     */
    @Override
    public int filterOrder() {
        return 1;
    }

    /**
     * 返回一个boolean值来判断该过滤器是否要执行，true：执行，false：不执行
     */
    @Override
    public boolean shouldFilter() {
        return !request.getRequestURI().startsWith("/user/log") && !request.getRequestURI().startsWith("/img");
    }

    /**
     * 过滤器的具体逻辑
     */
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        try {
            Claims claims = JwtUtil.parseJWT(request.getHeader("token"));

            if (claims == null) {
                log.warn("Token解析错误, url:{}", request.getRequestURI());
//                ctx.setResponseStatusCode(300);
//                ctx.setResponseBody(getBaseRspStr(300, "Token解析错误"));
//                return null;
            }
        } catch (Exception e) {
            log.warn("Token解析错误, url:{}", request.getRequestURI());
//            ctx.setResponseStatusCode(300);
//            ctx.setResponseBody(getBaseRspStr(300, "Token解析错误"));
//            return null;
        }

        String sessionId = request.getSession().getId();
        ctx.addZuulRequestHeader("Cloud-Cookie", "SESSION=" + sessionId);
        ctx.setSendZuulResponse(true);
        ctx.setResponseStatusCode(200);

        return null;
    }

    public static String getBaseRspStr(int code, String msg) {
        JSONObject jsonObject = new JSONObject();
        JSONObject base = new JSONObject();
        base.put("code", code);
        base.put("msg", msg);
        jsonObject.put("base", base);
        return jsonObject.toJSONString();
    }
}
