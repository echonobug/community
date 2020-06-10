package fun.jwei.community.controller;

import com.github.pagehelper.PageInfo;
import fun.jwei.community.dto.CommentAndUserDTO;
import fun.jwei.community.dto.CommentDTO;
import fun.jwei.community.dto.QuestionAndUserDTO;
import fun.jwei.community.model.Comment;
import fun.jwei.community.model.User;
import fun.jwei.community.result.Result;
import fun.jwei.community.result.ResultEnum;
import fun.jwei.community.service.CommentService;
import fun.jwei.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {
    private CommentService commentService;
    private QuestionService questionService;

    @ResponseBody
    @PostMapping("/comment")
    public Result<Comment> comment(@RequestBody CommentDTO commentDTO,
                                   HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        commentService.insert(commentDTO, user.getId());
        return new Result<>(ResultEnum.SUCCESS);
    }

    @PostMapping("/comment/comment")
    public String comment(@RequestParam("id") Long id, Model model) {
        QuestionAndUserDTO questionAndUserDTO = questionService.findById(id);
        PageInfo<CommentAndUserDTO> pageInfo = commentService.queryByTypeAndParentId(1, id, 1, 5);
        model.addAttribute("questionAndUser", questionAndUserDTO);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("pageAction", "/question/" + id + "?page=");
        return "list :: comment-list";
    }

    @PostMapping("/comment/secondary")
    public String secondaryComment(@RequestParam("id") Long id, Model model) {
        List<CommentAndUserDTO> secondaryCommentAndUserDAO = commentService.getSecondaryCommentAndUserDAO(id);
        model.addAttribute("id", id);
        model.addAttribute("list", secondaryCommentAndUserDAO);
        return "secondary-comment :: secondary-list";
    }


    @ResponseBody
    @PostMapping("/like")
    public Result<Long> like(@RequestParam("id") Long id) {
        return new Result<>(ResultEnum.SUCCESS, commentService.incLike(id));
    }

    @Autowired
    public void setQuestionService(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Autowired
    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }
}
