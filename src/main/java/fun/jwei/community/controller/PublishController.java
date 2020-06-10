package fun.jwei.community.controller;

import fun.jwei.community.mapper.QuestionMapper;
import fun.jwei.community.model.Question;
import fun.jwei.community.model.User;
import fun.jwei.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    private QuestionMapper questionMapper;
    private QuestionService questionService;

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Long id, Model model) {
        Question question = questionMapper.selectByPrimaryKey(id);
        model.addAttribute("id", id);
        model.addAttribute("title", question.getTitle());
        model.addAttribute("desc", question.getDescription());
        model.addAttribute("tag", question.getTag());
        return "publish";
    }

    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(@RequestParam("id") Long id,
                            @RequestParam("title") String title,
                            @RequestParam("desc") String desc,
                            @RequestParam("tag") String tag,
                            HttpServletRequest request,
                            Model model) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "用户未登录！");
            return "publish";
        }
        questionService.createOrUpdate(id,title,desc,tag,user.getId());
        return "redirect:/";
    }

    @Autowired
    public void setQuestionMapper(QuestionMapper questionMapper) {
        this.questionMapper = questionMapper;
    }

    @Autowired
    public void setQuestionService(QuestionService questionService) {
        this.questionService = questionService;
    }
}
