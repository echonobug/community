package fun.jwei.community.service;


import com.github.pagehelper.PageInfo;
import fun.jwei.community.dto.NotificationDTO;

public interface NotificationService {
    PageInfo<NotificationDTO> findReply(Long id, Integer page, Integer pageSize) ;

    long getUnreadReplyCount(Long id);

    void read(Long readId);
}
