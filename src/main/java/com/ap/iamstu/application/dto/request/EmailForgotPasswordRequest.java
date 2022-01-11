package com.ap.iamstu.application.dto.request;

import com.ap.iamstu.infrastructure.support.query.request.Request;
import com.ap.iamstu.infrastructure.support.validate.ValidateConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = true)
@Data
public class EmailForgotPasswordRequest extends Request {

    @NotBlank(message = "EMAIL_REQUIRED")
    @Size(max = ValidateConstraint.LENGTH.NAME_MAX_LENGTH, message = "EMAIL_LENGTH")
    private String email;
}
