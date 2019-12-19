package top.itcat.exception;

import top.itcat.exception.code.CodeConst;

public class NotEnoughException extends CommonException {
    public NotEnoughException() {
        super("条件不足", CodeConst.NotEnoughExceptionCode.getValue());
    }

    public NotEnoughException(String msg, String promt, int code) {
        super(msg, promt, code);
    }

    public NotEnoughException withPromt(String promt) {
        return new NotEnoughException(getMessage(), promt, getCode());
    }

}
