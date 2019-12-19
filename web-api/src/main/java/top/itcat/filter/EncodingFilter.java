package top.itcat.filter;

import org.springframework.stereotype.Component;
import top.itcat.util.GetBaseResponUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class EncodingFilter implements Filter {
    @Override
    public void init(javax.servlet.FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        servletRequest.setCharacterEncoding("UTF-8");
        servletResponse.setCharacterEncoding("UTF-8");

        try {
            filterChain.doFilter(req, res);
        } catch (Exception e) {
            ((HttpServletResponse) servletResponse).setStatus(400);
            servletResponse.getWriter().println(GetBaseResponUtil.getBaseRspStr(400, "错误请求参数"));
        }
    }

    @Override
    public void destroy() {

    }
}
