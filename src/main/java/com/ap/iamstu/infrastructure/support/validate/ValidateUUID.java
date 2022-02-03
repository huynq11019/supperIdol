package com.ap.iamstu.infrastructure.support.validate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({FIELD, PARAMETER, CONSTRUCTOR, METHOD, TYPE_USE})
@Documented
@Constraint(validatedBy = IdValidator.class)
public @interface ValidateUUID {
    String message() default "{Value is not invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
