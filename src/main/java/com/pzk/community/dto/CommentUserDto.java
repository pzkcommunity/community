package com.pzk.community.dto;

import com.pzk.community.model.User;
import lombok.Data;

/**
 * user和comment封装类
 */
@Data
public class CommentUserDto {
    private Long id;
    private Long parentId;
    private Integer type;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private String content;
    private User user;

}
