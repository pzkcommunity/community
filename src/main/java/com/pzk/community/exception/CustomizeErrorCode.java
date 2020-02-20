package com.pzk.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode{

    QUESTION_NOT_FOUND(2001,"你找的问题不存在了，要不要换一个试试？"),
    TARGET_PARENTID_NOT_FOUND(2002,"未选中问题或评论进行回复"),
    NOT_LOGIN(2003,"当前操作需要登录执行"),
    SYS_ERROR(2004,"服务过于繁忙，请稍后再试试！！"),
    TYPE_PARENT_WRONG(2005,"评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2006,"评论不存在"),
    CONTENT_NOT_EMPTY(2007,"输入内容不能为空！");

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }


    private String message;
    private Integer code;

    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }
}
