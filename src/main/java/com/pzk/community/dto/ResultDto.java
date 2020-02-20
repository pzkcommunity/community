package com.pzk.community.dto;

import com.pzk.community.exception.CustomizeErrorCode;
import com.pzk.community.exception.CustomizeException;
import lombok.Data;

@Data
public class ResultDto {
    private Integer code;
    private String message;

    public static ResultDto errorOf(Integer code,String message){
        ResultDto resultDto = new ResultDto();
        resultDto.setCode(code);
        resultDto.setMessage(message);

        return resultDto;
    }
    //返回错误代码 2003,操作需要登录
    public static ResultDto errorOf(CustomizeErrorCode errorCode){
        ResultDto resultDto = new ResultDto();
        resultDto.setCode(errorCode.getCode());
        resultDto.setMessage(errorCode.getMessage());
        return resultDto;
    }


    //成功 200
    public static ResultDto okOf(){
        ResultDto resultDto = new ResultDto();
        resultDto.setCode(200);
        resultDto.setMessage("请求成功！");
        return resultDto;
    }

    public static ResultDto errorOf(CustomizeException e){
        return errorOf(e.getCode(),e.getMessage());
    }
}
