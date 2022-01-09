package com.ap.iamstu.infrastructure.support.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InvalidInputResponse extends ErrorResponse<Void> {

    private Set<FieldErrorResponse> errors;

    public InvalidInputResponse(int code, String message, String error, Set<FieldErrorResponse> errors) {
        super(code, message, null, error);
        this.errors = errors;
    }

    public InvalidInputResponse(int code, String message, String error) {
        super(code, message, null, error);
        this.errors = null;
    }
}
