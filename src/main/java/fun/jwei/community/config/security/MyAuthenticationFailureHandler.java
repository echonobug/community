package fun.jwei.community.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import fun.jwei.community.result.Result;
import fun.jwei.community.result.ResultEnum;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MyAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        String errorMsg = "用户名或者密码输入错误!";
        if (exception instanceof SessionAuthenticationException) {
            errorMsg = exception.getMessage();
        }
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(new Result<>(ResultEnum.LOGIN_FAIL, errorMsg)));
    }
}
