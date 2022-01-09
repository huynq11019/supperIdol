package com.ap.iamstu.infrastructure.support.error;

import lombok.Getter;

@Getter
public enum NotFoundError implements ResponseError {
    NOT_FOUND(4040001, "Not found"),
    USER_NOT_FOUND(4040001, "User not found: {0}"),
    ;

    private final Integer code;
    private final String message;

    NotFoundError(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getName() {
        return name();
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public int getStatus() {
        return 404;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}