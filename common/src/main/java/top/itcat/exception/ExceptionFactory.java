package top.itcat.exception;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static top.itcat.exception.code.CodeConst.*;

public class ExceptionFactory {
    private static final HashMap<Integer, CommonException> ceMap = new HashMap<>();

    static {
        buildCommonException();
    }

    public static CommonException getCommonExceptionByCode(int code) {
        CommonException ce = ceMap.get(code);
        return ce == null ? new WrongStatusCodeException() : ce;
    }

    private static void buildCommonException() {
        ceMap.put(WrongStatusCodeExceptionCode.getValue(), new WrongStatusCodeException());
        ceMap.put(RateLimitExceptionCode.getValue(), new RateLimitException());
        ceMap.put(InternalExceptionCode.getValue(), new InternalException());

        ceMap.put(InvalidParamExceptionCode.getValue(), new InvalidParamException());
        ceMap.put(UnauthorizedExceptionCode.getValue(), new UnauthorizedException());
        ceMap.put(UnknownUserExceptionCode.getValue(), new UnknownUserException());
        ceMap.put(ExcessiveAttemptsExceptionCode.getValue(), new ExcessiveAttemptsException());

        ceMap.put(NotEnoughExceptionCode.getValue(), new NotEnoughException());
        ceMap.put(EmptyResultExceptionCode.getValue(), new NotEnoughException());
    }
}
