package top.itcat.exception;

import top.itcat.exception.code.CodeConst;

public class InternalException extends CommonException {
    public InternalException() {
        super("服务内部错误", CodeConst.InternalExceptionCode.getValue());
    }

    public InternalException(String msg, String promt, int code) {
        super(msg, promt, code);
    }

    public InternalException withPromt(String promt) {
        return new InternalException(getMessage(), promt, getCode());
    }
}
