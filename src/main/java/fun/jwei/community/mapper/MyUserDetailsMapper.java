package fun.jwei.community.mapper;

import fun.jwei.community.config.security.MyUserDetails;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MyUserDetailsMapper {

    @Select("SELECT id username,password FROM user u WHERE u.id = #{username}")
    MyUserDetails loadByUsername(@Param("username") String username);
}
