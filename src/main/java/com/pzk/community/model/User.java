package com.pzk.community.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private Integer id;
    private String account_id;
    private String name;
    private String token;
    private long gmt_create;
    private long gmt_modified;
    private String bio;
    private String avatar_url;


}
