package life.majiang.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {

    // 枚举类的所有实例必须在第一行显示列出！！
    QUESTION_NOT_FOUND(2001, "你找到问题不在了，要不要换个试试？"),
    ;

    private Integer code;
    private String message;

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }
}
