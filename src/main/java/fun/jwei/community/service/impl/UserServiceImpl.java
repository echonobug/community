package fun.jwei.community.service.impl;

import fun.jwei.community.dto.GithubUser;
import fun.jwei.community.mapper.UserMapper;
import fun.jwei.community.model.User;
import fun.jwei.community.model.UserExample;
import fun.jwei.community.service.UserService;
import fun.jwei.community.util.MyConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private UserMapper userMapper;

    @Override
    public Long createOrUpdate(GithubUser githubUser) {
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andAccountIdEqualTo(githubUser.getId())
                .andUserTypeEqualTo(MyConstants.GITHUB_USER);
        List<User> users = userMapper.selectByExample(userExample);
        User user;
        if (users.size() == 0) {
            user = new User();
            updateUser(githubUser, user);
            user.setAccountId(githubUser.getId());
            user.setGmtCreate(System.currentTimeMillis());
            user.setUserType(MyConstants.GITHUB_USER);
            userMapper.insertSelective(user);
        } else {
            user = users.get(0);
            updateUser(githubUser, user);
            userMapper.updateByPrimaryKey(user);
        }
        return user.getId();
    }

    private void updateUser(GithubUser githubUser, User user) {
        user.setName(githubUser.getName());
        user.setGmtModified(System.currentTimeMillis());
        user.setAvatarUrl(githubUser.getAvatarUrl());
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
}
