package top.itcat.handler;


import com.googlecode.protobuf.format.JsonFormat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import top.itcat.exception.CommonException;
import top.itcat.exception.InternalException;
import top.itcat.exception.InvalidParamException;
import top.itcat.pb_gen.common.Base;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public String defaultErrorHandler(HttpServletRequest req, Exception e) {
        Base.EmptyResponse.Builder rspBuilder = Base.EmptyResponse.newBuilder();
        Base.BaseResponse.Builder baseBuilder = Base.BaseResponse.newBuilder();

//        if (e instanceof RateLimitException) {
//            baseBuilder.setMsg("服务器繁忙，请稍后重试");
//        } else if (e instanceof EmptyResultException) {
//            baseBuilder.setMsg("请求参数有误");
//        } else
//        if (e instanceof CommonException) {
//            CommonException ce = (CommonException) e;
//            setPromt(baseBuilder, ce);
//            logCommonException(ce);
//        } else {
//            baseBuilder.setMsg("服务内部错误");
//            log.error("GlobalExceptionHandler Abnormal Exception,err:{}", e);
//        }

        if (e instanceof org.springframework.web.servlet.NoHandlerFoundException) {
            baseBuilder.setCode(404);
            baseBuilder.setMsg("资源未找到");
        } else if (e instanceof BindException) {
            baseBuilder.setCode(400);
            baseBuilder.setMsg("请求参数错误");
        } else {
            baseBuilder.setCode(500);
            baseBuilder.setMsg("服务内部错误");
        }

        log.error("GlobalExceptionHandler Abnormal Exception,err:{}", e);
        rspBuilder.setBase(baseBuilder.build());
        return JsonFormat.printToString(rspBuilder.build());
    }

    private void setPromt(Base.BaseResponse.Builder builder, CommonException ce) {
        if (ce instanceof InvalidParamException) {
            builder.setMsg(ce.getPromt());
        } else if (ce instanceof InternalException) {
            builder.setMsg(ce.getPromt());
        } else {
            builder.setMsg("服务内部错误");
        }
    }

    private void logCommonException(CommonException ce) {
        log.error("err:", ce);
    }
}