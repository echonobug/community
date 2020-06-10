package fun.jwei.community.exception;

public enum ExceptionEnum {
    NOT_LOGIN(10001,"您还没有登录呢！"),
    QUESTION_NOT_FOUND(10002,"不存在该提问，或该问题已被删除！");

    private Integer code;
    private String message;

    ExceptionEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
