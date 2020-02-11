package com.pzk.community.dto;

import lombok.Data;

/**
 * gitHubUser对象 获取gitHub 的user
 */
@Data
public class GitHubUser {
    private Integer id;
    private String name;
    /**
     * 个性描述
     */
    private String bio;
    private String avatar_url;


}
