package com.ryhnik.exception;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.List;

@ControllerAdvice
public class ControllerExceptionHandler {

    private static final String VALIDATION_ERROR = "Fields are not valid";
    private final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(MasterClubException.class)
    public ResponseEntity<ApiErrorResponse> masterClubExceptionHandler(MasterClubException exception) {
        logException(exception, exception.toString());
        return buildException(exception, exception.getStatus());
    }

    @ExceptionHandler({
            RuntimeException.class, IllegalArgumentException.class, HibernateException.class,
            PropertyReferenceException.class, EntityNotFoundException.class, DataIntegrityViolationException.class,
            HttpMessageNotReadableException.class
    })
    public ResponseEntity<ApiErrorResponse> commonExceptionHandler(Exception exception) {
        return buildException(exception);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiErrorResponse> validationExceptionHandler(ValidationException exception) {
        Throwable cause = exception.getCause();
        if (cause instanceof ConstraintViolationException e) {
            return constraintViolationExceptionHandler(e);
        } else if (cause instanceof MasterClubValidationException) {
            return buildException((Exception) cause);
        }
        return buildException(exception);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> methodArgumentNotValidExceptionHandler(
            MethodArgumentNotValidException exception) {
        String exceptionId = MasterClubException.generateExceptionId();
        logException(exception, exceptionId);

        List<ValidationErrorField> errors = exception.getAllErrors().stream()
                .map(p -> {
                    if (p instanceof FieldError f) {
                        return new ValidationErrorField(f.getField(), f.getDefaultMessage());
                    } else {
                        return new ValidationErrorField(p.getObjectName(), p.getDefaultMessage());
                    }
                }).toList();

        ValidationErrorResponse apiException = ValidationResponseBuilder.builder(exceptionId)
                .withMessage(VALIDATION_ERROR)
                .withErrors(errors)
                .withCode(HttpStatus.BAD_REQUEST.value())
                .build();

        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiErrorResponse> constraintViolationExceptionHandler(ConstraintViolationException e) {
        String exceptionId = MasterClubException.generateExceptionId();
        logException(e, exceptionId);

        List<String> errors = e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .toList();

        ApiErrorResponse apiException = ApiResponseBuilder.builder(exceptionId)
                .withMessage(String.join(", ", errors))
                .withErrors(errors)
                .withCode(HttpStatus.BAD_REQUEST.value())
                .build();

        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiErrorResponse> accessDeniedExceptionHandler(AccessDeniedException e) {
        String exceptionId = MasterClubException.generateExceptionId();
        logException(e, exceptionId);

        ApiErrorResponse apiException = ApiResponseBuilder.builder(exceptionId)
                .withMessage(e.getMessage())
                .withError(e.getMessage())
                .withCode(HttpStatus.UNAUTHORIZED.value())
                .build();

        return new ResponseEntity<>(apiException, HttpStatus.UNAUTHORIZED);
    }

    private ResponseEntity<ApiErrorResponse> buildException(MasterClubException e, HttpStatus status) {
        ApiErrorResponse apiException = ApiResponseBuilder.builder(e.getId())
                .withCode(e.getCode().getIntValue())
                .withMessage(e.getMessage())
                .withError(e.getMessage())
                .build();

        return new ResponseEntity<>(apiException, status);
    }

    private ResponseEntity<ApiErrorResponse> buildException(Exception e) {
        String exceptionId = MasterClubException.generateExceptionId();
        logException(e, exceptionId);

        ApiErrorResponse apiException = ApiResponseBuilder.builder(exceptionId)
                .withCode(Code.SYSTEM_ERROR.getIntValue())
                .withMessage(e.getMessage())
                .withError(e.getMessage())
                .build();

        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

    private void logException(Exception exception, String exceptionId) {
        logger.error("Exception: {}", exceptionId, exception);
    }
}
