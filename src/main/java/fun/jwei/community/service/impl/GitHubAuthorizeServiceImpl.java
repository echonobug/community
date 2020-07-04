package fun.jwei.community.service.impl;

import fun.jwei.community.config.security.MyUserDetails;
import fun.jwei.community.config.security.MyUserDetailsService;
import fun.jwei.community.dto.AccessTokenDTO;
import fun.jwei.community.dto.GithubUser;
import fun.jwei.community.mapper.UserMapper;
import fun.jwei.community.model.User;
import fun.jwei.community.service.GitHubAuthorizeService;
import fun.jwei.community.service.UserService;
import fun.jwei.community.utils3rd.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Service
public class GitHubAuthorizeServiceImpl implements GitHubAuthorizeService {
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    private GithubProvider githubProvider;
    private UserService userService;
    private UserMapper userMapper;

    @Resource
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    public void setGithubProvider(GithubProvider githubProvider) {
        this.githubProvider = githubProvider;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }


    @Override
    public boolean authorize(String code, String state, HttpServletRequest request) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if (githubUser == null) {
            return false;
        }
        Long uid = userService.createOrUpdate(githubUser);
        UserDetails userDetails = myUserDetailsService.loadUserByUsername(String.valueOf(uid));
        UsernamePasswordAuthenticationToken authentication
                = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = userMapper.selectByPrimaryKey(uid);
        request.getSession().setAttribute("user", user);
        return true;
    }
}
