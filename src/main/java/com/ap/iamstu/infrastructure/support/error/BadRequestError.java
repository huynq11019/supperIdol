package com.ap.iamstu.infrastructure.support.error;

import lombok.Getter;

@Getter
public enum BadRequestError implements ResponseError {
    INVALID_INPUT(4000000, "Invalid input : {0}"),
    INVALID_ACCEPT_LANGUAGE(4000001, "Invalid value for request header Accept-Language: {0}"),
    MISSING_PATH_VARIABLE(4000002, "Missing path variable"), //MissingPathVariable
    PATH_INVALID(4000003, "Path is invalid"),
    UNDEFINED(4000000, "");

    private final Integer code;
    private final String message;

    BadRequestError(Integer code, String message) {
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
        return 400;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
