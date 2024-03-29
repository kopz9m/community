package life.majiang.community.enums;

public enum CommentTypeEnum {
    QUESTION(1),
    COMMENT(2);

    public Integer getType() {
        return type;
    }
    private Integer type;
    CommentTypeEnum(Integer type) {
        this.type = type;
    }

    public static boolean isExist(Integer type) {
        for (CommentTypeEnum commentTypeEnum : CommentTypeEnum.values()) {
            if (commentTypeEnum.getType() == type) {
                return true;
            }
        }
        return false;
    }
}
