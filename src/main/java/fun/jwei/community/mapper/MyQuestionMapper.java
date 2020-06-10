package fun.jwei.community.mapper;

import fun.jwei.community.model.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface MyQuestionMapper {

    @Update("update question set view_count=view_count+1 " +
            "where id=#{id}")
    void incView(Long id);

    @Update("update question set comment_count=comment_count+1 " +
            "where id=#{id}")
    void incComment(Long id);

    @Select("select * from question where id != #{id} and tag REGEXP #{tag}")
    List<Question> findRelatedQuestion(Long id, @Param("tag") String tag);

    @Select("select * from question order by view_count desc limit #{count}")
    List<Question> findPopularQuestion(Integer count);
}
