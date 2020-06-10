package fun.jwei.community.exception;

public class CustomException extends RuntimeException {
    private Integer code;
    private String message;

    public CustomException(ExceptionEnum exceptionEnum) {
        this.code = exceptionEnum.getCode();
        this.message = exceptionEnum.getMessage();
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
