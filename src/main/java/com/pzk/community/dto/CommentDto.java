package com.pzk.community.dto;

import lombok.Data;

@Data
public class CommentDto {
    private Long parentId;
    private String content;
    private int type;
}
