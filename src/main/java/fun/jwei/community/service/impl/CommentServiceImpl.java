package fun.jwei.community.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import fun.jwei.community.dto.CommentAndUserDTO;
import fun.jwei.community.dto.CommentDTO;
import fun.jwei.community.enums.CommentTypeEnum;
import fun.jwei.community.enums.NotificationEnum;
import fun.jwei.community.mapper.*;
import fun.jwei.community.model.*;
import fun.jwei.community.service.CommentService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private UserMapper userMapper;
    private CommentMapper commentMapper;
    private QuestionMapper questionMapper;
    private NotificationMapper notificationMapper;
    private MyQuestionMapper myQuestionMapper;
    private MyCommentMapper myCommentMapper;

    @Override
    @Transactional
    public void insert(CommentDTO commentDTO, Long id) {
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentDTO, comment);
        comment.setCreatorId(id);
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        commentMapper.insertSelective(comment);
        Notification notification = new Notification();
        notification.setInitiator(id);
        notification.setGmtCreate(comment.getGmtCreate());
        notification.setContentId(comment.getId());
        if (comment.getType() == CommentTypeEnum.Question.getType()) {
            myQuestionMapper.incComment(comment.getParentId());
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            notification.setReceiver(question.getCreatorId());
            notification.setType(NotificationEnum.QuestionReply.getType());
        } else {
            myCommentMapper.incReply(comment.getParentId());
            Comment secondaryComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            notification.setReceiver(secondaryComment.getCreatorId());
            notification.setType(NotificationEnum.SecondaryReply.getType());
        }
        notificationMapper.insertSelective(notification);
    }

    @Override
    public PageInfo<CommentAndUserDTO> queryByTypeAndParentId(int type, Long id, Integer page, Integer pageSize) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andTypeEqualTo(type).andParentIdEqualTo(id);
        commentExample.setOrderByClause("gmt_create desc");
        PageHelper.startPage(page, pageSize);
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        PageInfo<Comment> commentPageInfo = new PageInfo<>(comments, 5);
        List<CommentAndUserDTO> list = getCommentAndUserDTO(comments);
        PageInfo<CommentAndUserDTO> pageInfo = new PageInfo<>();
        BeanUtils.copyProperties(commentPageInfo, pageInfo);
        pageInfo.setList(list);
        return pageInfo;
    }

    @NotNull
    private List<CommentAndUserDTO> getCommentAndUserDTO(List<Comment> comments) {
        List<CommentAndUserDTO> list = new ArrayList<>();
        for (Comment comment : comments) {
            User user = userMapper.selectByPrimaryKey(comment.getCreatorId());
            CommentAndUserDTO commentAndUserDTO = new CommentAndUserDTO();
            commentAndUserDTO.setComment(comment);
            commentAndUserDTO.setUser(user);
            list.add(commentAndUserDTO);
        }
        return list;
    }

    @Override
    public Long incLike(Long id) {
        myCommentMapper.incLike(id);
        Comment comment = commentMapper.selectByPrimaryKey(id);
        return comment.getLikeCount();
    }

    @Override
    public List<CommentAndUserDTO> getSecondaryCommentAndUserDAO(Long id) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andTypeEqualTo(2).andParentIdEqualTo(id);
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        return getCommentAndUserDTO(comments);
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    public void setCommentMapper(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

    @Autowired
    public void setMyQuestionMapper(MyQuestionMapper myQuestionMapper) {
        this.myQuestionMapper = myQuestionMapper;
    }

    @Autowired
    public void setMyCommentMapper(MyCommentMapper myCommentMapper) {
        this.myCommentMapper = myCommentMapper;
    }

    @Autowired
    public void setNotificationMapper(NotificationMapper notificationMapper) {
        this.notificationMapper = notificationMapper;
    }

    @Autowired
    public void setQuestionMapper(QuestionMapper questionMapper) {
        this.questionMapper = questionMapper;
    }
}
