package fun.jwei.community.config.security;

import fun.jwei.community.mapper.UserMapper;
import fun.jwei.community.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MyRememberMeAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Resource
    private UserMapper userMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String id = ((MyUserDetails) authentication.getPrincipal()).getUsername();
        User user = userMapper.selectByPrimaryKey(Long.valueOf(id));
        request.getSession().setAttribute("user", user);
    }
}
