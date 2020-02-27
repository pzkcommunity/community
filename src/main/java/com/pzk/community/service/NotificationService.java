package com.pzk.community.service;

import com.pzk.community.dto.NotificationDto;
import com.pzk.community.enums.NotificationStatusEnum;
import com.pzk.community.exception.CustomizeErrorCode;
import com.pzk.community.exception.CustomizeException;
import com.pzk.community.mapper.CommentMapper;
import com.pzk.community.mapper.NotificationMapper;
import com.pzk.community.mapper.QuestionMapper;
import com.pzk.community.mapper.UserMapper;
import com.pzk.community.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CommentMapper commentMapper;


    /**
     * 根据user id查出 对应获取的通知
     * @param id
     * @return
     */
    public List<NotificationDto> getNotification(Long id) {

        //用于前后端交互
        List<NotificationDto> notificationDtoList = new ArrayList<>();

        NotificationExample example = new NotificationExample();
        example.createCriteria()
                .andReceiverEqualTo(id);
        example.setOrderByClause("gmt_create desc");
        List<Notification> notifications = notificationMapper.selectByExample(example);
        if (notifications != null){
            for (Notification notification : notifications) {
                if (notification.getType() == 1){
                    //根据OuterId 从question中查出 回复问题的title
                    Long questionId = notification.getOuterid();
                    Question question = questionMapper.selectByPrimaryKey(questionId);
                    String title = question.getTitle();
                    //根据notifier 从user中查出  notifier名字
                    Long userId = notification.getNotifier();
                    User user = userMapper.selectByPrimaryKey(userId);
                    String name = user.getName();

                    //给NotifiedDto 赋值
                    NotificationDto notificationDto = new NotificationDto();
                    notificationDto.setTitle(title);
                    notificationDto.setNotifierName(name);
                    notificationDto.setQuestionId(question.getId());
                    notificationDto.setNotification(notification);

                    //添加到List<NotificationDto>
                    notificationDtoList.add(notificationDto);
                } else if (notification.getType() == 2){
                        //根据OuterId 从comment中查出 回复某条问题下的评论
                        Long commentId = notification.getOuterid();
                        Comment comment = commentMapper.selectByPrimaryKey(commentId);
                        Long questionId = comment.getParentId();
                        Question question = questionMapper.selectByPrimaryKey(questionId);
                        String title = question.getTitle();
                        //根据notifier 从user中查出  notifier名字
                        Long userId = notification.getNotifier();
                        User user = userMapper.selectByPrimaryKey(userId);
                        String name = user.getName();

                        //给NotifiedDto 赋值
                        NotificationDto notificationDto = new NotificationDto();
                        notificationDto.setTitle(title);
                        notificationDto.setNotifierName(name);
                        notificationDto.setQuestionId(question.getId());
                        notificationDto.setNotification(notification);

                        //添加到List<NotificationDto>
                        notificationDtoList.add(notificationDto);
                    }
                }
        }

        return notificationDtoList;
    }


    /**
     * 点击通知信息时
     * @param id
     * @param user
     * @return
     */
    public NotificationDto read(Long id, User user) {

        Notification notification = notificationMapper.selectByPrimaryKey(id);
        //通知的问题不见了
        if (notification == null){
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_QUESTION_NOT_FOUND);
        }
        //标记为已读
        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKey(notification);

        //赋值给NotificationDto
        NotificationDto notificationDto = new NotificationDto();
        if (notification.getType() == 1){
            //根据OuterId 从question中查出 回复问题的title
            Long questionId = notification.getOuterid();
            Question question = questionMapper.selectByPrimaryKey(questionId);
            String title = question.getTitle();
            //根据notifier 从user中查出  notifier名字
            Long userId = notification.getNotifier();
            String name = user.getName();

            //给NotifiedDto 赋值
            notificationDto.setTitle(title);
            notificationDto.setNotifierName(name);
            notificationDto.setQuestionId(question.getId());
            notificationDto.setNotification(notification);
        } else if (notification.getType() == 2){
            //根据OuterId 从comment中查出 回复某条问题下的评论
            Long commentId = notification.getOuterid();
            Comment comment = commentMapper.selectByPrimaryKey(commentId);
            Long questionId = comment.getParentId();
            Question question = questionMapper.selectByPrimaryKey(questionId);
            String title = question.getTitle();
            //根据notifier 从user中查出  notifier名字
            Long userId = notification.getNotifier();
            String name = user.getName();

            //给NotifiedDto 赋值
            notificationDto.setTitle(title);
            notificationDto.setNotifierName(name);
            notificationDto.setQuestionId(question.getId());
            notificationDto.setNotification(notification);
        }
        return notificationDto;
    }

    /**
     * 未读通知数
     * @param id
     * @return
     */
    public int getUnReadCount(Long id) {
        NotificationExample example = new NotificationExample();
        example.createCriteria()
                .andStatusEqualTo(0)
                .andReceiverEqualTo(id);

        List<Notification> notifications = notificationMapper.selectByExample(example);

        return notifications.size();

    }
}
