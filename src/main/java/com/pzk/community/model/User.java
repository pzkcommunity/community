package com.pzk.community.model;

import com.pzk.community.mapper.IQuestionMapper;
import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private Integer id;
    private String accountId;
    private String name;
    private String token;
    private long gmtCreate;
    private long gmtModified;
    private String bio;
    private String avatarUrl;


}
