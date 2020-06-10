package fun.jwei.community.dto;

import lombok.Data;

@Data
public class CommentDTO {
    private Integer type;
    private Long parentId;
    private String content;
}
