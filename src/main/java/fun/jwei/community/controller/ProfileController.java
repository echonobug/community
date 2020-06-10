package fun.jwei.community.controller;

import com.github.pagehelper.PageInfo;
import fun.jwei.community.model.User;
import fun.jwei.community.service.NotificationService;
import fun.jwei.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {
    private QuestionService questionService;
    private NotificationService notificationService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable String action,
                          Model model,
                          HttpServletRequest request,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize) {
        model.addAttribute("action", action);
        User user = (User) request.getSession().getAttribute("user");
        PageInfo<?> pageInfo = null;
        String actionName = "";
        String listFragment = "";
        if (action.equals("myQuestion")) {
            actionName = "我的提问";
            listFragment = "list";
            pageInfo = questionService.findByCreatorId(user.getId(), page, pageSize);
        } else if (action.equals("questionReply")) {
            actionName = "最新回复";
            listFragment = "notification-list";
            pageInfo = notificationService.findReply(user.getId(), page, pageSize);
        } else {
            pageInfo = new PageInfo<>();
        }
        Long unreadCount = notificationService.getUnreadReplyCount(user.getId());
        model.addAttribute("unreadCount",unreadCount);
        model.addAttribute("actionName",actionName);
        model.addAttribute("listFragment",listFragment);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("pageAction","/profile/"+action+"?page=");
        return "profile";
    }

    @Autowired
    public void setQuestionService(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Autowired
    public void setNotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
}
