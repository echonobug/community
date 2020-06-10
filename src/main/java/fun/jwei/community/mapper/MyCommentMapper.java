package fun.jwei.community.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MyCommentMapper {

    @Update("update comment set like_count=like_count+1 " +
            "where id=#{id}")
    void incLike(Long id);

    @Update("update comment set reply_count=reply_count+1 " +
            "where id=#{id}")
    void incReply(Long id);
}
