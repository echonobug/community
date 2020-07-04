package fun.jwei.community.config.security;

import fun.jwei.community.mapper.MyUserDetailsMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class MyUserDetailsService implements UserDetailsService {

    @Resource
    private MyUserDetailsMapper myUserDetailsMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUserDetails myUserDetails = myUserDetailsMapper.loadByUsername(username);
        if (myUserDetails == null) {
            throw new UsernameNotFoundException("用户" + username + "不存在");
        }
        return myUserDetails;
    }
}
