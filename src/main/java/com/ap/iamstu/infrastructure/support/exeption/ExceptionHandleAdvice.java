package com.ap.iamstu.infrastructure.support.exeption;

import com.ap.iamstu.infrastructure.support.error.*;
import com.ap.iamstu.infrastructure.support.i18n.LocaleStringService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
//import com.mbamc.common.dto.error.ErrorResponse;
//import com.mbamc.common.dto.error.FieldErrorResponse;
//import com.mbamc.common.dto.error.InvalidInputResponse;
//import com.mbamc.common.error.*;
//import com.mbamc.common.webapp.i18n.LocaleStringService;
//import feign.RetryableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.client.circuitbreaker.NoFallbackAvailableException;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.NonTransientDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
@Order
public class ExceptionHandleAdvice {

    private final LocaleStringService localeStringService;

    @Autowired
    public ExceptionHandleAdvice(LocaleStringService localeStringService) {
        this.localeStringService = localeStringService;
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse<Void>> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException e, HttpServletRequest request) {
        log.warn("Failed to handle request " + request.getRequestURI() + ": " + e.getMessage());
        Set<FieldErrorResponse> errors = new HashSet<>();
        errors.add(FieldErrorResponse.builder()
                .field(e.getParameter().getParameterName())
                .message(e.getMessage()).build());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new InvalidInputResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        localeStringService.getMessage(
                                BadRequestError.INVALID_INPUT.getName(),
                                "Invalid request arguments"),
                        BadRequestError.INVALID_INPUT.getName(),
                        errors));
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorResponse<Void>> handleIllegalStateException(
            MaxUploadSizeExceededException e, HttpServletRequest request) {
        log.warn("Failed to handle request file Upload" + request.getRequestURI() + ": " + e.getMessage());
        Set<FieldErrorResponse> errors = new HashSet<>();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new InvalidInputResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        localeStringService.getMessage(
                                BadRequestError.FILE_SIZE_EXCEEDED.getName(),
                                BadRequestError.FILE_SIZE_EXCEEDED.getMessage()),
                        BadRequestError.INVALID_INPUT.getName(),
                        errors));
    }

    @ExceptionHandler(MissingPathVariableException.class)
    public ResponseEntity<ErrorResponse<Void>> handleMissingPathVariableException(
            MissingPathVariableException e, HttpServletRequest request) {
        log.warn("Failed to handle request " + request.getRequestURI() + ": " + e.getMessage());
        Set<FieldErrorResponse> errors = new HashSet<>();
        errors.add(FieldErrorResponse.builder()
                .field(e.getParameter().getParameterName())
                .message("Missing path variable").build());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new InvalidInputResponse(
                        BadRequestError.MISSING_PATH_VARIABLE.getCode(),
                        localeStringService.getMessage(
                                BadRequestError.INVALID_INPUT.getName(),
                                "Invalid request arguments"),
                        BadRequestError.MISSING_PATH_VARIABLE.getName(),
                        errors));
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ErrorResponse<Void>> handleMissingRequestHeaderException(
            MissingRequestHeaderException e, HttpServletRequest request) {
        log.warn("Failed to handle request " + request.getRequestURI() + ": " + e.getMessage());
        Set<FieldErrorResponse> errors = new HashSet<>();
        errors.add(FieldErrorResponse.builder()
                .field(e.getParameter().getParameterName())
                .message("Missing request header").build());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new InvalidInputResponse(
                        BadRequestError.MISSING_PATH_VARIABLE.getCode(),
                        localeStringService.getMessage(
                                BadRequestError.INVALID_INPUT.getName(),
                                "Invalid request arguments"),
                        BadRequestError.MISSING_PATH_VARIABLE.getName(),
                        errors));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse<Void>> handleRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
        log.warn("Failed to handle request " + request.getRequestURI() + ": " + e.getMessage());
        Set<FieldErrorResponse> errors = new HashSet<>();
        errors.add(FieldErrorResponse.builder()
                .field(e.getMethod())
                .message("Http request method not support").build());
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(new InvalidInputResponse(
                        HttpStatus.METHOD_NOT_ALLOWED.value(),
                        localeStringService.getMessage(
                                BadRequestError.INVALID_INPUT.getName(),
                                "Invalid request arguments"),
                        BadRequestError.INVALID_INPUT.getName(),
                        errors));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<InvalidInputResponse> handleValidationException(
            ConstraintViolationException e, HttpServletRequest request) {
        String queryParam;
        String object;
        String errorMessage;
        Set<FieldErrorResponse> errors = new HashSet<>();
        for (ConstraintViolation constraintViolation : e.getConstraintViolations()) {
            String queryParamPath = constraintViolation.getPropertyPath().toString();
            log.debug("queryParamPath = {}", queryParamPath);
            queryParam = queryParamPath.contains(".") ?
                    queryParamPath.substring(queryParamPath.indexOf(".") + 1) :
                    queryParamPath;
            object = queryParamPath.split("\\.").length > 1 ?
                    queryParamPath.substring(queryParamPath.indexOf(".") + 1, queryParamPath.lastIndexOf(".")) :
                    queryParamPath;
            errorMessage = localeStringService.getMessage(
                    constraintViolation.getMessage(), constraintViolation.getMessage());
            errors.add(FieldErrorResponse.builder()
                    .field(queryParam)
                    .objectName(object)
                    .message(errorMessage).build());
        }
        InvalidInputResponse invalidInputResponse;
        if (errors.size() >= 1) {
            long count = errors.size();
            invalidInputResponse = errors.stream().skip(count - 1).findFirst()
                    .map(fieldErrorResponse -> new InvalidInputResponse(
                            HttpStatus.BAD_REQUEST.value(),
                            localeStringService.getMessage(
                                    BadRequestError.INVALID_INPUT.getName(),
                                    "Invalid request arguments"),
                            fieldErrorResponse.getObjectName(),
                            errors))
                    .orElse(null);
        } else {
            invalidInputResponse = new InvalidInputResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    localeStringService.getMessage(
                            BadRequestError.INVALID_INPUT.getName(),
                            "Invalid request arguments"),
                    BadRequestError.INVALID_INPUT.getName(),
                    errors);
        }
        log.warn("Failed to handle request " + request.getRequestURI() + ": " + e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(invalidInputResponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<InvalidInputResponse> handleValidationException(
            HttpMessageNotReadableException e, HttpServletRequest request) throws IOException {
        log.warn("Failed to handle request " + request.getRequestURI() + ": " + e.getMessage(), e);
        String message = "Invalid input format";
        Throwable cause = e.getCause();
        InvalidInputResponse invalidInputResponse;
        if (cause instanceof InvalidFormatException) {
            InvalidFormatException invalidFormatException = (InvalidFormatException) cause;
            String fieldPath = invalidFormatException.getPath().stream()
                    .map(JsonMappingException.Reference::getFieldName)
                    .collect(Collectors.joining("."));

            // custom error with enum
            if (invalidFormatException.getTargetType() != null && invalidFormatException.getTargetType().isEnum()) {
                message = String.format("Invalid enum value: '%s' for the field: '%s'. The value must be one of: %s.",
                        invalidFormatException.getValue(),
                        fieldPath,
                        Arrays.toString(invalidFormatException.getTargetType().getEnumConstants()));
            }
            invalidInputResponse = new InvalidInputResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    localeStringService.getMessage(
                            BadRequestError.INVALID_INPUT.getName(),
                            "Invalid request arguments"),
                    BadRequestError.INVALID_INPUT.name(),
                    Collections.singleton(FieldErrorResponse.builder()
                            .field(fieldPath)
                            .message(message).build())
            );
        } else if (cause instanceof JsonParseException) {
            JsonParseException jsonParseException = (JsonParseException) cause;
            invalidInputResponse = new InvalidInputResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    localeStringService.getMessage(
                            BadRequestError.INVALID_INPUT.getName(),
                            "Invalid request arguments"),
                    BadRequestError.INVALID_INPUT.name(),
                    Collections.singleton(FieldErrorResponse.builder()
                            .field(jsonParseException.getProcessor().getCurrentName())
                            .message("Invalid input format").build())
            );
        } else if (cause instanceof MismatchedInputException) {
            MismatchedInputException mismatchedInputException = (MismatchedInputException) cause;
            invalidInputResponse = new InvalidInputResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    localeStringService.getMessage(
                            BadRequestError.INVALID_INPUT.getName(),
                            "Invalid request arguments"),
                    BadRequestError.INVALID_INPUT.getName(),
                    Collections.singleton(FieldErrorResponse.builder()
                            .field(mismatchedInputException.getPath().stream()
                                    .map(JsonMappingException.Reference::getFieldName)
                                    .collect(Collectors.joining(".")))
                            .message("Mismatched input").build()));
        } else if (cause instanceof JsonMappingException) {
            JsonMappingException jsonMappingException = (JsonMappingException) cause;
            invalidInputResponse = new InvalidInputResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    localeStringService.getMessage(
                            BadRequestError.INVALID_INPUT.getName(),
                            "Invalid request arguments"),
                    BadRequestError.INVALID_INPUT.getName(),
                    Collections.singleton(FieldErrorResponse.builder()
                            .field(jsonMappingException.getPath().stream()
                                    .map(JsonMappingException.Reference::getFieldName)
                                    .collect(Collectors.joining(".")))
                            .message("Json mapping invalid").build()));
        } else {
            invalidInputResponse = new InvalidInputResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    localeStringService.getMessage(
                            BadRequestError.INVALID_INPUT.getName(),
                            "Invalid request arguments"),
                    BadRequestError.INVALID_INPUT.getName(),
                    Collections.singleton(FieldErrorResponse.builder()
                            .message("Invalid input").build()));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(invalidInputResponse);
    }

    @ExceptionHandler(ResponseException.class)
    public ResponseEntity<ErrorResponse<Object>> handleResponseException(ResponseException e, HttpServletRequest request) {
        log.warn("Failed to handle request {}: {}", request.getRequestURI(), e.getError().getMessage(), e);
        ResponseError error = e.getError();
        String message = localeStringService.getMessage(error.getName(), e.getError().getMessage(), e.getParams());
        return ResponseEntity.status(error.getStatus())
                .body(ErrorResponse.builder()
                        .code(error.getCode())
                        .error(error.getName())
                        .message(message)
                        .build());
    }

    @ExceptionHandler(InvocationTargetException.class)
    public ResponseEntity<ErrorResponse<Void>> handleResponseException(InvocationTargetException e, HttpServletRequest request) {
        log.warn("Failed to handle request {}: {}", request.getRequestURI(), e.getMessage(), e);
        ResponseError error = InternalServerError.INTERNAL_SERVER_ERROR;
        log.error("Failed to handle request " + request.getRequestURI() + ": " + error.getMessage(), e);
        String msg = localeStringService.getMessage(
                InternalServerError.INTERNAL_SERVER_ERROR.getName(), "There are somethings wrong: {0}", e);
        return ResponseEntity.status(error.getStatus())
                .body(ErrorResponse.<Void>builder()
                        .code(error.getCode())
                        .error(error.getName())
                        .message(msg)
                        .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<InvalidInputResponse> handleValidationException(MethodArgumentNotValidException e, HttpServletRequest request) {
        BindingResult bindingResult = e.getBindingResult();
        Set<FieldErrorResponse> fieldErrors = bindingResult.getAllErrors().stream()
                .map(objectError -> {
                    try {
                        FieldError fieldError = (FieldError) objectError;
                        String message = localeStringService.getMessage(
                                fieldError.getDefaultMessage(), fieldError.getDefaultMessage());
                        return FieldErrorResponse.builder()
                                .field(fieldError.getField())
                                .objectName(fieldError.getObjectName())
                                .message(message)
                                .build();
                    } catch (ClassCastException ex) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        log.warn("Failed to handle request " + request.getRequestURI() + ": " + e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new InvalidInputResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        localeStringService.getMessage(
                                BadRequestError.INVALID_INPUT.getName(),
                                "Invalid request arguments"),
                        BadRequestError.INVALID_INPUT.getName(),
                        fieldErrors));
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorResponse<Void>> handleResponseException(Exception e, HttpServletRequest request) {
        ResponseError error = InternalServerError.INTERNAL_SERVER_ERROR;
        log.error("Failed to handle request " + request.getRequestURI() + ": " + error.getMessage(), e);
        String msg = localeStringService.getMessage(
                InternalServerError.INTERNAL_SERVER_ERROR.getName(), "There are somethings wrong: {0}", e);
        return ResponseEntity.status(error.getStatus())
                .body(ErrorResponse.<Void>builder()
                        .code(error.getCode())
                        .error(error.getName())
                        .message(msg)
                        .build());
    }

//    @ExceptionHandler({RetryableException.class})
//    public ResponseEntity<ErrorResponse<Void>> handleResponseException(RetryableException e, HttpServletRequest request) {
//        ResponseError error = ServiceUnavailableError.SERVICE_UNAVAILABLE_ERROR;
//        log.error("Failed to handle request " + request.getRequestURI() + ": " + error.getMessage(), e);
//        String msg = localeStringService.getMessage(
//                ServiceUnavailableError.SERVICE_UNAVAILABLE_ERROR.getName(), ServiceUnavailableError.SERVICE_UNAVAILABLE_ERROR.getMessage());
//        return ResponseEntity.status(error.getStatus())
//                .body(ErrorResponse.<Void>builder()
//                        .code(error.getCode())
//                        .error(error.getName())
//                        .message(msg)
//                        .build());
//    }

//    @ExceptionHandler({NoFallbackAvailableException.class})
//    public ResponseEntity<ErrorResponse<Void>> handleResponseException(NoFallbackAvailableException e, HttpServletRequest request) {
//        ResponseError error = ServiceUnavailableError.SERVICE_UNAVAILABLE_ERROR;
//        log.error("Failed to handle request " + request.getRequestURI() + ": " + error.getMessage(), e);
//        String msg = localeStringService.getMessage(
//                ServiceUnavailableError.SERVICE_UNAVAILABLE_ERROR.getName(), ServiceUnavailableError.SERVICE_UNAVAILABLE_ERROR.getMessage());
//        return ResponseEntity.status(error.getStatus())
//                .body(ErrorResponse.<Void>builder()
//                        .code(error.getCode())
//                        .error(error.getName())
//                        .message(msg)
//                        .build());
//    }

    @ExceptionHandler({DataIntegrityViolationException.class, NonTransientDataAccessException.class, DataAccessException.class})
    public ResponseEntity<ErrorResponse<Void>> handleDataAccessException(DataAccessException e, HttpServletRequest request) {
        ResponseError error = InternalServerError.DATA_ACCESS_EXCEPTION;
        log.error("Failed to handle request " + request.getRequestURI() + ": " + error.getMessage(), e);
        log.error(e.getMessage(), e);
        String msg = localeStringService.getMessage(
                InternalServerError.DATA_ACCESS_EXCEPTION.getName(), "Data access exception", e.getClass().getName());
        return ResponseEntity.status(error.getStatus())
                .body(ErrorResponse.<Void>builder()
                        .code(error.getCode())
                        .error(error.getName())
                        .message(msg)
                        .build());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<InvalidInputResponse> handleValidationException(
            MissingServletRequestParameterException e, HttpServletRequest request) {
        log.warn("Failed to handle request " + request.getRequestURI() + ": " + e.getMessage(), e);
        String message = localeStringService.getMessage(
                BadRequestError.INVALID_INPUT.getName(),
                "Invalid request arguments");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new InvalidInputResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        message,
                        BadRequestError.INVALID_INPUT.getName(),
                        Collections.singleton(FieldErrorResponse.builder()
                                .field(e.getParameterName())
                                .message(e.getMessage())
                                .build())));
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<InvalidInputResponse> handleValidationException(
            MissingServletRequestPartException e, HttpServletRequest request) {
        log.warn("Failed to handle request " + request.getRequestURI() + ": " + e.getMessage(), e);
        String message = localeStringService.getMessage(
                BadRequestError.INVALID_INPUT.getName(),
                "Invalid request arguments");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new InvalidInputResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        message,
                        BadRequestError.INVALID_INPUT.getName()));
    }


    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<InvalidInputResponse> handleValidationException(
            MultipartException e, HttpServletRequest request) {
        log.warn("Failed to handle request " + request.getRequestURI() + ": " + e.getMessage(), e);
        String message = localeStringService.getMessage(
                BadRequestError.INVALID_INPUT.getName(),
                "Invalid request arguments");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new InvalidInputResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        message,
                        BadRequestError.INVALID_INPUT.getName()));
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<InvalidInputResponse> handleValidationException(
            BindException e, HttpServletRequest request) {
        log.warn("Failed to handle request " + request.getRequestURI() + ": " + e.getMessage(), e);
        Set<FieldErrorResponse> fieldsErrors = e.getFieldErrors().stream()
                .map(fieldError -> FieldErrorResponse.builder()
                        .field(fieldError.getField())
                        .objectName(fieldError.getObjectName())
                        .build())
                .collect(Collectors.toSet());
        String message = localeStringService.getMessage(
                BadRequestError.INVALID_INPUT.getName(),
                "Invalid request arguments");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new InvalidInputResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        message,
                        BadRequestError.INVALID_INPUT.name(),
                        fieldsErrors
                ));
    }

    @ExceptionHandler(MismatchedInputException.class)
    public ResponseEntity<InvalidInputResponse> handleValidationException(
            MismatchedInputException e, HttpServletRequest request) {
        log.warn("Failed to handle request " + request.getRequestURI() + ": " + e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new InvalidInputResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        localeStringService.getMessage(
                                BadRequestError.INVALID_INPUT.getName(),
                                "Invalid request arguments"),
                        BadRequestError.INVALID_INPUT.getName(),
                        Collections.singleton(FieldErrorResponse.builder()
                                .message(e.getMessage())
                                .build())));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse<Void>> handleValidationException(
            AccessDeniedException e, HttpServletRequest request) {
        log.warn("Failed to handle request " + request.getRequestURI() + ": " + e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ErrorResponse.<Void>builder()
                        .error(AuthorizationError.ACCESS_DENIED.getName())
                        .message("You were not permitted to request " + request.getMethod() + " " + request.getRequestURI())
                        .build());
    }

    @ExceptionHandler(InsufficientAuthenticationException.class)
    public ResponseEntity<ErrorResponse<Void>> handleValidationException(
            InsufficientAuthenticationException e, HttpServletRequest request) {
        log.warn("Failed to handle request " + request.getRequestURI() + ": " + e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ErrorResponse.<Void>builder()
                        .error(AuthenticationError.UNAUTHORISED.getName())
                        .message("You were not authorized to request " + request.getMethod() + " " + request.getRequestURI())
                        .build());
    }

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public ResponseEntity<ErrorResponse<Void>> handleValidationException(
            InternalAuthenticationServiceException e, HttpServletRequest request) {
        log.warn("Failed to handle request " + request.getRequestURI() + ": " + e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ErrorResponse.<Void>builder()
                        .error(AuthenticationError.UNAUTHORISED.getName())
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse<Void>> handleValidationException(
            BadCredentialsException e, HttpServletRequest request) {
        log.warn("Failed to handle request " + request.getRequestURI() + ": " + e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ErrorResponse.<Void>builder()
                        .error(AuthenticationError.UNAUTHORISED.getName())
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(ForwardInnerAlertException.class)
    public ResponseEntity<ErrorResponse<Void>> handleValidationException(
            ForwardInnerAlertException e, HttpServletRequest request) {
        log.warn("Failed to handle request " + request.getRequestURI() + ": " + e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.<Void>builder()
                        .error(e.getResponse().getError())
                        .message(e.getResponse().getMessage())
                        .code(e.getResponse().getCode())
                        .build());
    }
}
