package fun.jwei.community.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import fun.jwei.community.mapper.UserMapper;
import fun.jwei.community.model.User;
import fun.jwei.community.result.Result;
import fun.jwei.community.result.ResultEnum;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Resource
    private UserMapper userMapper;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String id = ((MyUserDetails) authentication.getPrincipal()).getUsername();
        User user = userMapper.selectByPrimaryKey(Long.valueOf(id));
        request.getSession().setAttribute("user", user);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(new Result<>(ResultEnum.LOGIN_SUCCESS, "/")));
    }
}

