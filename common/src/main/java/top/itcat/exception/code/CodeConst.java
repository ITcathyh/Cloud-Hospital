package top.itcat.exception.code;

public enum CodeConst {
    WrongStatusCodeExceptionCode(1000),
    RateLimitExceptionCode(1001),
    InternalExceptionCode(1002),

    InvalidParamExceptionCode(2000),
    UnauthorizedExceptionCode(2001),
    UnknownUserExceptionCode(2002),
    ExcessiveAttemptsExceptionCode(2003),

    NotEnoughExceptionCode(3000),
    EmptyResultExceptionCode(3001);

    private int value;

    private CodeConst(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
