package com.ap.iamstu.infrastructure.support.exeption;



import com.ap.iamstu.infrastructure.support.error.ResponseError;

import java.text.MessageFormat;

public class ResponseException extends RuntimeException {
    private ResponseError error;
    private Object[] params;

    public ResponseException(ResponseError error) {
        this(error.getMessage(), null, error);
    }

    public ResponseException(String message, ResponseError error) {
        this(message, null, error);
    }

    public ResponseException(String message, Throwable cause, ResponseError error) {
        this(message, cause, error, null);
    }

    public ResponseException(String message, ResponseError error, Object... params) {
        this(message, null, error, params);
    }

    public ResponseException(String message, Throwable cause, ResponseError error, Object... params) {
        super(MessageFormat.format(message, params), cause);
        this.error = error;
        this.params = params == null ? new Object[0] : params;
    }

    public ResponseError getError() {
        return this.error;
    }

    public void setError(final ResponseError error) {
        this.error = error;
    }

    public Object[] getParams() {
        return this.params;
    }

    public void setParams(final Object[] params) {
        this.params = params;
    }
}
