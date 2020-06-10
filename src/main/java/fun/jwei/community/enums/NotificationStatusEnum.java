package fun.jwei.community.enums;

public enum NotificationStatusEnum {
    Unread(0),
    Read(1);

    private Integer status;

    NotificationStatusEnum(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
