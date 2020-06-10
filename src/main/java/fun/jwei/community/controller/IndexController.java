package fun.jwei.community.controller;

import com.github.pagehelper.PageInfo;
import fun.jwei.community.dto.QuestionAndUserDTO;
import fun.jwei.community.model.Question;
import fun.jwei.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {
    private QuestionService questionService;

    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize) {
        List<Question> popularQuestions = questionService.findPopularQuestion(10);
        PageInfo<QuestionAndUserDTO> pageInfo = questionService.getAll(page, pageSize);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("pageAction", "/?page=");
        model.addAttribute("popularQuestions", popularQuestions);
        return "index";
    }

    @GetMapping("/search")
    public String search(@RequestParam(name = "keyword", defaultValue = "") String keyword,
                         @RequestParam(name = "page", defaultValue = "1") Integer page,
                         @RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize,
                         Model model, HttpServletRequest request) {
        if (!keyword.equals("")) {
            request.getSession().setAttribute("indexSearchKeyword", keyword);
        } else {
            keyword = (String) request.getSession().getAttribute("indexSearchKeyword");
        }
        List<Question> popularQuestions = questionService.findPopularQuestion(10);
        PageInfo<QuestionAndUserDTO> pageInfo = questionService.findAll(keyword, page, pageSize);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("pageAction", "/search?page=");
        model.addAttribute("popularQuestions", popularQuestions);
        return "index";
    }

    @Autowired
    public void setQuestionService(QuestionService questionService) {
        this.questionService = questionService;
    }
}
