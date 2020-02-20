package com.pzk.community.dto;

import com.pzk.community.model.User;
import lombok.Data;

/**
 * question and user组装类
 */
@Data
public class QuestionDto {
    private Long id;
    private String title;
    private String description;
    private long gmtCreate;
    private long gmtModified;
    private Long creator;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private String tag;
    private User user;
}
