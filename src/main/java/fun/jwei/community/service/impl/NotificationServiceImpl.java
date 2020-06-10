package fun.jwei.community.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import fun.jwei.community.dto.NotificationDTO;
import fun.jwei.community.enums.NotificationEnum;
import fun.jwei.community.enums.NotificationStatusEnum;
import fun.jwei.community.mapper.CommentMapper;
import fun.jwei.community.mapper.NotificationMapper;
import fun.jwei.community.mapper.QuestionMapper;
import fun.jwei.community.mapper.UserMapper;
import fun.jwei.community.model.*;
import fun.jwei.community.service.NotificationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {
    private NotificationMapper notificationMapper;
    private UserMapper userMapper;
    private CommentMapper commentMapper;
    private QuestionMapper questionMapper;

    @Override
    public PageInfo<NotificationDTO> findReply(Long id, Integer page, Integer pageSize) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(id);
        notificationExample.setOrderByClause("gmt_create desc");
        PageHelper.startPage(page, pageSize);
        List<Notification> notifications = notificationMapper.selectByExample(notificationExample);
        PageInfo<Notification> notificationPageInfo = new PageInfo<>(notifications);
        List<NotificationDTO> notificationDTOList = new ArrayList<>();
        for (Notification notification : notificationPageInfo.getList()) {
            NotificationDTO notificationDTO = new NotificationDTO();
            notificationDTO.setId(notification.getId());
            notificationDTO.setGmt_reply(notification.getGmtCreate());
            notificationDTO.setStatus(notification.getStatus());
            notificationDTO.setType(notification.getType());
            User user = userMapper.selectByPrimaryKey(notification.getInitiator());
            notificationDTO.setUserName(user.getName());
            Comment comment = commentMapper.selectByPrimaryKey(notification.getContentId());
            Question question;
            if (notification.getType() == NotificationEnum.QuestionReply.getType()) {
                question = questionMapper.selectByPrimaryKey(comment.getParentId());
            } else {
                Comment comment1 = commentMapper.selectByPrimaryKey(comment.getParentId());
                question = questionMapper.selectByPrimaryKey(comment1.getParentId());
            }
            notificationDTO.setQuestionId(question.getId());
            notificationDTO.setQuestionTitle(question.getTitle());
            notificationDTOList.add(notificationDTO);
        }
        PageInfo<NotificationDTO> pageInfo = new PageInfo<>();
        BeanUtils.copyProperties(notificationPageInfo, pageInfo);
        pageInfo.setList(notificationDTOList);
        return pageInfo;
    }

    @Override
    public long getUnreadReplyCount(Long id) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(id).andStatusEqualTo(NotificationStatusEnum.Unread.getStatus());
        return notificationMapper.countByExample(notificationExample);
    }

    @Override
    public void read(Long readId) {
        Notification notification = notificationMapper.selectByPrimaryKey(readId);
        notification.setStatus(NotificationStatusEnum.Read.getStatus());
        notificationMapper.updateByPrimaryKey(notification);
    }


    @Autowired
    public void setNotificationMapper(NotificationMapper notificationMapper) {
        this.notificationMapper = notificationMapper;
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
    public void setQuestionMapper(QuestionMapper questionMapper) {
        this.questionMapper = questionMapper;
    }
}
