package fun.jwei.community.controller;

import fun.jwei.community.mapper.UserMapper;
import fun.jwei.community.model.User;
import fun.jwei.community.result.Result;
import fun.jwei.community.result.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {

    private UserMapper userMapper;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
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

    @ResponseBody
    @PostMapping("editProfile")
    public Result<String> edit(User user) {
        userMapper.updateByPrimaryKeySelective(user);
        return new Result<>(ResultEnum.SUCCESS, "资料已更新!下次登录后刷新！！！");
    }

    @ResponseBody
    @PostMapping("setPassword")
    public Result<String> setPassword(String oldPassword, String password,
                                      HttpServletRequest request) {
        Object sessionUser = request.getSession().getAttribute("user");
        if (sessionUser == null || !(sessionUser instanceof User)) {
            return new Result<>(ResultEnum.SUCCESS, "请先登录！");
        }

        Long id = ((User) sessionUser).getId();
        User user = userMapper.selectByPrimaryKey(id);
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            return new Result<>(ResultEnum.SUCCESS, "密码修改失败！旧密码错误！");
        }
        user.setPassword(passwordEncoder.encode(password));
        userMapper.updateByPrimaryKeySelective(user);
        return new Result<>(ResultEnum.SUCCESS, "密码修改成功！");
    }

    @GetMapping("registerSuccess")
    public String registerSuccess(Model model, String id) {
        model.addAttribute("user_id", id);
        return "register-success";
    }
}
