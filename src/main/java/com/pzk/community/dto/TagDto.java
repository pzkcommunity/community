package com.pzk.community.dto;

import lombok.Data;

import java.util.List;

/**
 * 标签库传输类
 */
@Data
public class TagDto {
    private String categoryName;
    private List<String> tags;
}
