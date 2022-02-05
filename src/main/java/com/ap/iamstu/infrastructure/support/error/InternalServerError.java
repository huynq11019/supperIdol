package com.ap.iamstu.infrastructure.support.error;

public enum InternalServerError implements ResponseError {
    INTERNAL_SERVER_ERROR(50000001, "There are somethings wrong"),
    DATA_ACCESS_EXCEPTION(50000002, "Data access exception"),
    ;

    private final Integer code;
    private final String message;

    InternalServerError(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getName() {
        return this.name();
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public int getStatus() {
        return 500;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }
}
