package fun.jwei.community.service;

import com.github.pagehelper.PageInfo;
import fun.jwei.community.dto.QuestionAndUserDTO;
import fun.jwei.community.model.Question;

import java.util.List;

public interface QuestionService {
    PageInfo<QuestionAndUserDTO> getAll(Integer page, Integer pageSize);

    PageInfo<QuestionAndUserDTO> findAll(String keyword, Integer page, Integer pageSize);

    PageInfo<QuestionAndUserDTO> findByCreatorId(Long id, Integer page, Integer pageSize);

    QuestionAndUserDTO findById(Long id);

    void createOrUpdate(Long id, String title, String desc, String tag, Long userId);

    void incView(Long id);

    List<Question> findRelatedQuestion(Long id, String tag);

    List<Question> findPopularQuestion(Integer count);
}
