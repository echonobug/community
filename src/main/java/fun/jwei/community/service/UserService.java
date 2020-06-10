package fun.jwei.community.service;

import fun.jwei.community.dto.GithubUser;

public interface UserService {
    String createOrUpdate(GithubUser githubUser);
}
