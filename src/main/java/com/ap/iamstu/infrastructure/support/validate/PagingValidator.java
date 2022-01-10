package com.ap.iamstu.infrastructure.support.validate;


import com.ap.iamstu.application.dto.request.PagingRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.*;
import java.util.stream.Collectors;

public class PagingValidator implements ConstraintValidator<ValidatePaging, PagingRequest> {

    private List<String> allowedSorts;

    @Override
    public void initialize(ValidatePaging constraintAnnotation) {
        initAllowSorts(constraintAnnotation);
    }

    @Override
    public boolean isValid(PagingRequest criteria, ConstraintValidatorContext context) {
        String sort = criteria.getSortBy();
        if (allowedSorts.isEmpty() || Objects.isNull(sort)) {
            return true;
        }
        sort = sort.trim().replaceAll(" ", "");
        Set<String> notAllowedSorts = new HashSet<>();
        notAllowedSorts.add(sort);

        notAllowedSorts.removeAll(allowedSorts);
        if (!notAllowedSorts.isEmpty()) {
            String notAllowedSortsMsg = String.join("; ", notAllowedSorts);
            ValidatorUtils.createErrorField(
                    context, "sort", "Not allow sort by: " + notAllowedSortsMsg, true);
            return false;
        }
        return true;
    }

    private void initAllowSorts(ValidatePaging constraintAnnotation) {
        List<String> baseAllowedSorts = Arrays.stream(constraintAnnotation.allowedSorts())
                .map(String::trim)
                .collect(Collectors.toList());

        List<String> ascAllowedSorts = baseAllowedSorts.stream()
                .map(sort -> String.format("%s.%s", sort, PagingRequest.ASC_SYMBOL))
                .collect(Collectors.toList());
        List<String> descAllowedSorts = baseAllowedSorts.stream()
                .map(sort -> String.format("%s.%s", sort, PagingRequest.DESC_SYMBOL))
                .collect(Collectors.toList());

        allowedSorts = new ArrayList<>();
        allowedSorts.addAll(baseAllowedSorts);
        allowedSorts.addAll(ascAllowedSorts);
        allowedSorts.addAll(descAllowedSorts);
    }
}
