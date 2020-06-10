package fun.jwei.community.dto;

import lombok.Data;

@Data
public class NotificationDTO {
    private Long id;
    private String userName;
    private Long questionId;
    private String questionTitle;
    private Integer type;
    private Long gmt_reply;
    private Integer status;
}
