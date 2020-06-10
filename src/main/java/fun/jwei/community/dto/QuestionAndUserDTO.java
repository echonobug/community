package fun.jwei.community.dto;

import fun.jwei.community.model.Question;
import fun.jwei.community.model.User;
import lombok.Data;

@Data
public class QuestionAndUserDTO {
    private Question question;
    private User user;
}
