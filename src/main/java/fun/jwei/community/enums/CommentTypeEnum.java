package fun.jwei.community.enums;

public enum CommentTypeEnum {
    Question(1),
    Comment(2);
    private Integer type;

    CommentTypeEnum(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }
}
