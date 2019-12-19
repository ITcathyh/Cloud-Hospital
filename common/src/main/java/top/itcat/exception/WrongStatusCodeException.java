package top.itcat.exception;

import top.itcat.exception.code.CodeConst;

public class WrongStatusCodeException extends CommonException {
    public WrongStatusCodeException() {
        super("状态码有误", CodeConst.WrongStatusCodeExceptionCode.getValue());
    }

    public WrongStatusCodeException(String msg, String promt, int code) {
        super(msg, promt, code);
    }

    public WrongStatusCodeException withPromt(String promt) {
        return new WrongStatusCodeException(getMessage(), promt, getCode());
    }
}
