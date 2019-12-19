package top.itcat.exception;

import top.itcat.exception.code.CodeConst;

public class ExcessiveAttemptsException extends CommonException {
    public ExcessiveAttemptsException() {
        super("状态码有误", CodeConst.ExcessiveAttemptsExceptionCode.getValue());
    }

    public ExcessiveAttemptsException(String msg, String promt, int code) {
        super(msg, promt, code);
    }

    public ExcessiveAttemptsException withPromt(String promt) {
        return new ExcessiveAttemptsException(getMessage(), promt, getCode());
    }
}
