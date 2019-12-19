package top.itcat.handler;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import top.itcat.aop.LineConvertHump;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Type;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LineToHumpHandler implements HandlerMethodArgumentResolver {
    private static MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LineConvertHump.class);
    }

    private String underLineToCamel(String str) {
        Matcher matcher = Pattern.compile("_(\\w)").matcher(str);
        StringBuffer sb = new StringBuffer();

        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }

        matcher.appendTail(sb);
        return sb.toString();
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        HttpServletRequest servletRequest = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        ServletServerHttpRequest inputMessage = new ServletServerHttpRequest(servletRequest);
        Object result = null;

        try {
            Type genericParameterType = methodParameter.getGenericParameterType();
            String contentType = servletRequest.getContentType();

            if (MediaType.APPLICATION_JSON_VALUE.equals(contentType) ||
                    MediaType.APPLICATION_JSON_UTF8_VALUE.equals(contentType)) {
                result = converter.read(Class.forName(genericParameterType.getTypeName()), inputMessage);
            } else {
                nativeWebRequest.getNativeResponse(HttpServletResponse.class).setStatus(400);
                return null;

//                Object obj = BeanUtils.instantiate(methodParameter.getParameterType());
//                BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(obj);
//                Map<String, String[]> parameterMap = nativeWebRequest.getParameterMap();
//
//                for (Map.Entry<String, String[]> map : parameterMap.entrySet()) {
//                    String paramName = map.getKey();
//                    String[] paramValue = map.getValue();
//                    Field[] declaredFields = obj.getClass().getDeclaredFields();
//
//                    for (Field declaredField : declaredFields) {
//                        if (declaredField.getName().contains("_") && paramName.equals(declaredField.getName())) {
//                            wrapper.setPropertyValue(paramName, paramValue);
//                            break;
//                        }
//                        String underLineParamName = underLineToCamel(paramName);
//                        if (declaredField.getName().equals(underLineParamName)) {
//                            wrapper.setPropertyValue(underLineParamName, paramValue);
//                            break;
//                        }
//                    }
//                }
//                result = obj;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}