package top.itcat.exception;

public class CommonException extends RuntimeException {
    private final int code;
    private final String promt;

    public CommonException(String msg, int code) {
        this(msg, msg, code);
    }

    public CommonException(String msg, String promt, int code) {
        super(msg);
        this.code = code;
        this.promt = promt;
    }

    public int getCode() {
        return code;
    }

    public String getPromt() {
        return promt;
    }

    public CommonException withPromt(String promt) {
        return new CommonException(super.getMessage(), promt, code);
    }

    @Override
    public String toString() {
        return "CommonException{" +
                "code=" + code +
                "msg=" + getMessage() +
                '}';
    }
}