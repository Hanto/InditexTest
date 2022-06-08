package com.inditex.test.product.adapter.api.errorhandling;// Created by jhant on 26/05/2022.

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestErrorHandler extends ResponseEntityExceptionHandler
{
    // BAD REQUEST:
    //--------------------------------------------------------------------------------------------------------

    // Display the result of constaint violations (Entity Validation)
    //--------------------------------------------------------------------------------------------------------

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object>handleConstraintViolation(ConstraintViolationException ex, WebRequest request)
    {
        List<FieldErrors> fieldErrors = ex.getConstraintViolations().stream()
            .map(this::toFieldError)
            .collect(Collectors.toList());

        ApiError apiError = ApiError.builder()
            .status(HttpStatus.BAD_REQUEST)
            .shortMessage("Invalid Parameters")
            .localizedMessage(ex.getLocalizedMessage())
            .fieldErrors(fieldErrors)
            .build();

        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    // Argument is not the expected type
    //--------------------------------------------------------------------------------------------------------

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object>handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, WebRequest request)
    {
        String error = String.format("Invalid URL: parameter %s should be of type %s", ex.getName(),
            ex.getRequiredType() != null ? ex.getRequiredType().getCanonicalName() : "");

        ApiError apiError = ApiError.builder()
            .status(HttpStatus.BAD_REQUEST)
            .shortMessage(error)
            .localizedMessage(ex.getLocalizedMessage())
            .build();

        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    // NOT FOUND REQUEST:
    //--------------------------------------------------------------------------------------------------------

    // Throw when url doesn't exist, needs these application.properties:
    // spring.mvc.throw-exception-if-no-handler-found=true
    // spring.web.resources.add-mappings=false
    //--------------------------------------------------------------------------------------------------------

    @Override @NonNull
    protected ResponseEntity<Object> handleNoHandlerFoundException(
        NoHandlerFoundException ex, @NonNull HttpHeaders headers, @NonNull HttpStatus status, @NonNull WebRequest request)
    {
        String error = String.format("Invalid URL: No handler found for %s %s", ex.getHttpMethod(), ex.getRequestURL());

        ApiError apiError = ApiError.builder()
            .status(HttpStatus.NOT_FOUND)
            .shortMessage(error)
            .localizedMessage(ex.getLocalizedMessage())
            .build();

        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    // DEFAULT:
    //--------------------------------------------------------------------------------------------------------

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object>handleAll(Exception ex, WebRequest request)
    {
        String error = "Error Ocurred";

        ApiError apiError = ApiError.builder()
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .shortMessage(error)
            .localizedMessage(ex.getLocalizedMessage())
            .build();

        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    // HELPER:
    //--------------------------------------------------------------------------------------------------------

    private FieldErrors toFieldError(ConstraintViolation<?> violation)
    {
        return FieldErrors.builder()
            .entityName(violation.getRootBeanClass().getName())
            .fieldName(violation.getPropertyPath().toString())
            .fieldValue(violation.getInvalidValue().toString())
            .reason(violation.getMessage())
            .build();
    }
}