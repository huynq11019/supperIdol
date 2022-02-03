package com.ap.iamstu.application.dto.request;

import com.ap.iamstu.infrastructure.support.query.request.Request;
import com.ap.iamstu.infrastructure.support.validate.ValidateConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = true)
@Data
public class ClassCreateRequest extends Request {
    @Size(min = 1, max = ValidateConstraint.LENGTH.CODE_MAX_LENGTH)
    private String code;
    @NotBlank(message = "CLASS_NAME_REQUIRED")
    private String name;
    @Size( max = ValidateConstraint.LENGTH.DESC_MAX_LENGTH, message = "CLASS_DESC_MAX_LENGTH")
    private String description;
    @Size(max = ValidateConstraint.LENGTH.ID_MAX_LENGTH)
    private String fileId;

}
