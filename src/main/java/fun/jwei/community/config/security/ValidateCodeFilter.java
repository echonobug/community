package fun.jwei.community.config.security;

import fun.jwei.community.util.MyConstants;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

@Component
public class ValidateCodeFilter extends OncePerRequestFilter {

    @Resource
    MyAuthenticationFailureHandler myAuthenticationFailureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (request.getRequestURI().equals("/login")
                && request.getMethod().equalsIgnoreCase("post")) {
            try {
                //验证谜底与用户输入是否匹配
                validate(new ServletWebRequest(request));
            } catch (AuthenticationException e) {
                myAuthenticationFailureHandler.onAuthenticationFailure(request, response, e);
                return;
            }

        }
        filterChain.doFilter(request, response);
    }

    private void validate(ServletWebRequest request) throws ServletRequestBindingException {

        HttpSession session = request.getRequest().getSession();

        String codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), "vcode");
        if (codeInRequest.isEmpty()) {
            throw new SessionAuthenticationException("验证码不能为空");
        }

        String codeInSession = (String) session.getAttribute(MyConstants.VALIDATE_KEY);
        if (Objects.isNull(codeInSession)) {
            throw new SessionAuthenticationException("验证码不存在！");
        }

        if (!codeInSession.equalsIgnoreCase(codeInRequest)) {
            throw new SessionAuthenticationException("验证码不正确！");
        }
    }
}
