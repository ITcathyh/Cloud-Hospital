package top.itcat.exception;

import top.itcat.exception.code.CodeConst;

public class RateLimitException extends CommonException {
    public RateLimitException() {
        super("接口限流", CodeConst.RateLimitExceptionCode.getValue());
    }

    public RateLimitException(String msg, String promt, int code) {
        super(msg, promt, code);
    }

    public RateLimitException withPromt(String promt) {
        return new RateLimitException(getMessage(), promt, getCode());
    }
}
