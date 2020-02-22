package com.pzk.community.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pzk.community.model.User;
import lombok.Data;

/**
 * user和comment封装类
 */
@Data
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class CommentUserDto {
    private Long id;
    private Long parentId;
    private Integer type;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer likeCount;
    private Integer commentCount;
    private String content;
    private User user;

}
