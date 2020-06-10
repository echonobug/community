package fun.jwei.community.enums;

public enum NotificationEnum {
    QuestionReply(1),
    SecondaryReply(2),
    Like(3);

    private Integer type;

    NotificationEnum(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
