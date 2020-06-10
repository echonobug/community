package fun.jwei.community.controller;


import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/error")
public class CustomErrorController implements ErrorController {
    @Override
    public String getErrorPath() {
        return "error";
    }


    @RequestMapping(
            produces = {"text/html"}
    )
    public String errorHtml(HttpServletRequest request, Model model) {
        HttpStatus status = getStatus(request);
        if (status.is4xxClientError()) {
            model.addAttribute("message", "您访问的页面走丢了，试试其他页面？");
        }
        if (status.is5xxServerError()) {
            model.addAttribute("message", "服务器异常，请稍后再试！");
        }
        return "error";
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
