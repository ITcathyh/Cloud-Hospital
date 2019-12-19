package top.itcat.exception;

import top.itcat.exception.code.CodeConst;

public class EmptyResultException extends CommonException{
    public EmptyResultException() {
        super("空结果集", CodeConst.EmptyResultExceptionCode.getValue());
    }

    public EmptyResultException(String msg, String promt, int code) {
        super(msg, promt, code);
    }

    public EmptyResultException withPromt(String promt) {
        return new EmptyResultException(getMessage(), promt, getCode());
    }
}
