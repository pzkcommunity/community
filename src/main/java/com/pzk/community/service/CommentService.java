package com.pzk.community.service;

import com.pzk.community.dto.CommentUserDto;
import com.pzk.community.enums.CommentTypeEnum;
import com.pzk.community.enums.NotificationStatusEnum;
import com.pzk.community.enums.NotificationTypeEnum;
import com.pzk.community.exception.CustomizeErrorCode;
import com.pzk.community.exception.CustomizeException;
import com.pzk.community.mapper.CommentMapper;
import com.pzk.community.mapper.NotificationMapper;
import com.pzk.community.mapper.QuestionMapper;
import com.pzk.community.model.Comment;
import com.pzk.community.model.Notification;
import com.pzk.community.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private NotificationMapper notificationMapper;

    //事务
    @Transactional
    public void insert(Comment comment) {
        if (comment.getParentId() == null || comment.getParentId() == 0){
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARENTID_NOT_FOUND);
        }

        //评论为空 或者 既不是1 也不是2
        if(comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARENT_WRONG);
        }
        if (comment.getType() == CommentTypeEnum.Comment.getType()){
            //回复评论
            Comment dbcomment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbcomment == null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);
            //更新 一级评论的回复数
            Comment questionComment = new Comment();
            questionComment.setId(comment.getParentId());
            questionComment.setCommentCount(1);
            commentMapper.incCommentCount(questionComment);

            //存储通知
            saveNotify(comment, dbcomment.getCommentator(), NotificationTypeEnum.REPLY_COMMENT);
        } else{
            //回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question == null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            question.setCommentCount(1);
            questionMapper.incComment(question);

            saveNotify(comment, question.getCreator(), NotificationTypeEnum.REPLY_QUESTION);
        }
    }

    /**
     * 存储通知
     * @param comment 评论
     * @param receiver
     * @param notifyType
     */
    private void saveNotify(Comment comment, Long receiver, NotificationTypeEnum notifyType) {
        if(comment.getCommentator() == receiver){
            return;
        }
        Notification notification = new Notification();
        notification.setGmtCreate(comment.getGmtCreate());
        notification.setNotifier(comment.getCommentator());
        notification.setOuterid(comment.getParentId());
        notification.setReceiver(receiver);
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notification.setType(notifyType.getType());
        notificationMapper.insert(notification);
    }

    /**
     *
     * @param id     parentId
     * @param type   parentType  问题的回复(1) 评论的回复(2)
     * @return
     */
    public List<CommentUserDto> findByParentIdAndType(Long id, Integer type) {
        //根据问题的id查找出 对应的回复
        List<CommentUserDto> commentUserDtos = commentMapper.selectByParentIdAndType(id,
                type);


        return commentUserDtos;
    }
}
