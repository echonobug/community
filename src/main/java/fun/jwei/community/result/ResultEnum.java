package fun.jwei.community.result;

public enum ResultEnum {
    SUCCESS(0,"请求成功！"),
    LOGIN_SUCCESS(1000,"登录成功！"),
    LOGIN_FAIL(2100, "登录失败!");;

    private Integer code;
    private String message;

    ResultEnum(Integer code, String message) {
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
