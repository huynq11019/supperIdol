package com.ap.iamstu.infrastructure.support.validate;

import lombok.extern.log4j.Log4j2;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.UUID;

@Log4j2
public class IdValidator implements ConstraintValidator<ValidateUUID, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            // try parsing all ids
            UUID.fromString(value);
            //do something
            return true;
        } catch (IllegalArgumentException exception) {
            //handle the case where string is not valid UUID
            log.error("Invalid UUID: " + exception.getMessage());
            return false;
        }
    }
}
