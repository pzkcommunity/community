package com.pzk.community.dto;

import lombok.Data;

@Data
public class UploadDto {
    private Integer success;
    private String message;
    private String url;
}
