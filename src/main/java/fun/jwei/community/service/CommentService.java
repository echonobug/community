package fun.jwei.community.service;

import com.github.pagehelper.PageInfo;
import fun.jwei.community.dto.CommentAndUserDTO;
import fun.jwei.community.dto.CommentDTO;

import java.util.List;

public interface CommentService {
    void insert(CommentDTO commentDTO, Long id);

    PageInfo<CommentAndUserDTO> queryByTypeAndParentId(int type, Long id, Integer page, Integer pageSize);

    Long incLike(Long id);

    List<CommentAndUserDTO> getSecondaryCommentAndUserDAO(Long id);
}
