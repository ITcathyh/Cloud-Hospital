package top.itcat.exception;

import top.itcat.exception.code.CodeConst;

public class InvalidParamException extends CommonException {
    public InvalidParamException() {
        super("无效请求参数", CodeConst.InvalidParamExceptionCode.getValue());
    }

    public InvalidParamException(String msg, String promt, int code) {
        super(msg, promt, code);
    }

    public InvalidParamException withPromt(String promt) {
        return new InvalidParamException(getMessage(), promt, getCode());
    }
}