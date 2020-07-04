package fun.jwei.community.service;

import javax.servlet.http.HttpServletRequest;

public interface GitHubAuthorizeService {
    boolean authorize(String code, String state, HttpServletRequest request);
}
