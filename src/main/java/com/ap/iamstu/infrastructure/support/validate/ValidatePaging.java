package com.ap.iamstu.infrastructure.support.validate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PagingValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface ValidatePaging {
    String message() default "Paging is not valid";

    String[] allowedSorts() default {};

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
