package fun.jwei.community.controller;


import fun.jwei.community.mapper.UserMapper;
import fun.jwei.community.model.User;
import fun.jwei.community.result.Result;
import fun.jwei.community.result.ResultEnum;
import fun.jwei.community.util.GenVcode;
import fun.jwei.community.util.MyConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class LoginController {

    private GenVcode genVcode;
    private UserMapper userMapper;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setGenVcode(GenVcode genVcode) {
        this.genVcode = genVcode;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    @ResponseBody
    @GetMapping("/vcode")
    public void genvcode(HttpServletRequest request,
                         HttpServletResponse response,
                         String t) throws IOException {

        HttpSession session = request.getSession();
        response.setDateHeader("Expires", 0);
        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        // Set standard HTTP/1.0 no-cache header.
        response.setHeader("Pragma", "no-cache");
        // return a png
        response.setContentType("image/png");

        String vcode = genVcode.generateVCode(response.getOutputStream());

        session.setAttribute(MyConstants.VALIDATE_KEY, vcode);
    }

    @GetMapping("register")
    public String toRegister() {
        return "register";
    }

    @ResponseBody
    @PostMapping("doRegister")
    public Result<Long> doRegister(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userMapper.insertSelective(user);
        return new Result<>(ResultEnum.SUCCESS, user.getId());
    }

    @GetMapping("registerSuccess")
    public String registerSuccess(Model model, String id) {
        model.addAttribute("user_id", id);
        return "register-success";
    }
}
