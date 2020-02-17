package com.pzk.community.exception;

public class CustomizeExceptiion extends RuntimeException {
    private String message;

    public CustomizeExceptiion(String message) {
        this.message = message;
    }

    public CustomizeExceptiion(ICustomizeErrorCode errorCode) {
        this.message = errorCode.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
