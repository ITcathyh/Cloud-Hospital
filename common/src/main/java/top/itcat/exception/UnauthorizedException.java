package top.itcat.exception;

import top.itcat.exception.code.CodeConst;

public class UnauthorizedException extends CommonException {
    public UnauthorizedException() {
        super("无权限", CodeConst.UnauthorizedExceptionCode.getValue());
    }

    public UnauthorizedException(String msg, String promt, int code) {
        super(msg, promt, code);
    }

    public UnauthorizedException withPromt(String promt) {
        return new UnauthorizedException(getMessage(), promt, getCode());
    }
}
