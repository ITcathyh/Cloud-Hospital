package top.itcat.exception;

import top.itcat.exception.code.CodeConst;

public class UnknownUserException extends CommonException {
    public UnknownUserException() {
        super("用户信息有误", CodeConst.UnknownUserExceptionCode.getValue());
    }

    public UnknownUserException(String msg, String promt, int code) {
        super(msg, promt, code);
    }

    @Override
    public UnknownUserException withPromt(String promt) {
        return null;
    }
}
