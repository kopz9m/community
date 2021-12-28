package life.majiang.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {

    // 枚举类的所有实例必须在第一行显示列出！！
    QUESTION_NOT_FOUND(2001, "你找到问题不在了，要不要换个试试？"),
    TARGET_PARAM_NOT_FOUND(2002, "未选中任何问题或评论进行回复"),
    NO_LOGIN(2003, "当前操作需要登录，请登陆后重试"),
    SYS_ERROR(2004, "服务器失去连接"),
    TYPE_PARAM_WRONG(2005, "评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2006, "回复的评论不存在了"),
    CONTENT_IS_EMPTY(2007, "输入内容不能为空"),
    READ_NOTIFICATION_FAIL(2008, "没有权限"),
    NOTIFICATION_NOT_FOUND(2009, "消息莫非是不翼而飞了？"),
    FILE_UPLOAD_FAIL(2010, "图片上传失败"),
    INVALID_INPUT(2011, "非法输入"),
    INVALID_OPERATION(2012, "操作错误"),
    USER_DISABLE(2013, "操作被禁用，如有疑问请联系管理员"),
    RATE_LIMIT(2014, "操作太快了，请稍后重试"),
    WRONG_PASSWORD(2015, "密码错误或者用户不存在"),
    SEND_MAIL_FAIL(2016, "邮件验证码发送失败"),
    EMAIL_ALREADY_EXISTS(2017, "邮箱用户已存在！"),
    REGISTER_FAIL(2018, "注册失败！"),
    WRONG_VERIFICATION_CODE(2019, "验证码错误！"),
    EMAIL_DIFFER(2020, "邮箱不一致！")
    ;

    private Integer code;
    private String message;

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    CustomizeErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
