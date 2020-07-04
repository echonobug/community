package fun.jwei.community.controller;

import fun.jwei.community.dto.AccessTokenDTO;
import fun.jwei.community.dto.GithubUser;
import fun.jwei.community.service.UserService;
import fun.jwei.community.service.GitHubAuthorizeService;
import fun.jwei.community.utils3rd.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("github")
public class GithubAuthorizeController {

    private GitHubAuthorizeService gitHubAuthorizeService;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request) {
        gitHubAuthorizeService.authorize(code, state, request);
        return "redirect:/";
    }


    @Autowired
    public void setGitHubAuthorizeService(GitHubAuthorizeService gitHubAuthorizeService) {
        this.gitHubAuthorizeService = gitHubAuthorizeService;
    }
}
