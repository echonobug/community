package fun.jwei.community.service.impl;

import fun.jwei.community.dto.GithubUser;
import fun.jwei.community.mapper.UserMapper;
import fun.jwei.community.model.User;
import fun.jwei.community.model.UserExample;
import fun.jwei.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private UserMapper userMapper;
    @Override
    public String createOrUpdate(GithubUser githubUser) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(githubUser.getId());
        List<User> users = userMapper.selectByExample(userExample);
        User user;
        String token = UUID.randomUUID().toString();
        if(users.size() == 0){
            user = new User();
            updateUser(githubUser, user, token);
            user.setAccountId(githubUser.getId());
            user.setGmtCreate(System.currentTimeMillis());
            userMapper.insert(user);
        }else {
            user = users.get(0);
            updateUser(githubUser,user,token);
            userMapper.updateByPrimaryKey(user);
        }
        return user.getToken();
    }

    private void updateUser(GithubUser githubUser, User user, String token) {
        user.setName(githubUser.getName());
        user.setToken(token);
        user.setGmtModified(System.currentTimeMillis());
        user.setAvatarUrl(githubUser.getAvatarUrl());
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
}
