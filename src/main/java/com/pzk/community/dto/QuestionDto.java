package com.pzk.community.dto;

import com.pzk.community.domain.User;
import lombok.Data;

/**
 * question and user组装类
 */
@Data
public class QuestionDto {
    private Integer id;
    private String title;
    private String description;
    private long gmtCreate;
    private long gmtModified;
    private Integer creator;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private String tag;
    private User user;
}
