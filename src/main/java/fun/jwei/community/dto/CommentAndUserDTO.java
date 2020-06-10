package fun.jwei.community.dto;

import fun.jwei.community.model.Comment;
import fun.jwei.community.model.User;
import lombok.Data;

@Data
public class CommentAndUserDTO {
    private Comment comment;
    private User user;
}
