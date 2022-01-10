package com.ap.iamstu.infrastructure.support.error;

public enum AuthenticationError implements ResponseError {
    UNKNOWN(4010000, "UNKNOWN"),
    UNAUTHORISED(4010001, "Unauthorised"),
    FORBIDDEN_ACCESS_TOKEN(4010002, "Access token has been forbidden due to user has logged out or deactivated"),
    FORBIDDEN_REFRESH_TOKEN(4010003, "Refresh token has been forbidden: {0}"),
    INVALID_REFRESH_TOKEN(4010004, "Refresh token has been forbidden: {0}"),
    VALIDATE_EXPIRATION_TIME(4010005, "validate expiration time"),
    VALIDATE_TOKEN_ID(4010006, "JWT Token is not an ID Token"),
    VALIDATE_ISSUER(4010007, "Issuer does not match idp"),
    ;

    private final Integer code;
    private final String message;

    AuthenticationError(Integer code, String message) {
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
        return 401;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
