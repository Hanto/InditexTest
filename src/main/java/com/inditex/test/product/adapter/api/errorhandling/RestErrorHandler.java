package com.inditex.test.product.adapter.api.errorhandling;// Created by jhant on 26/05/2022.

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestErrorHandler extends ResponseEntityExceptionHandler
{
    // BAD REQUEST:
    //--------------------------------------------------------------------------------------------------------------------

    // Throw when argument anotated with @Valid failed: (DTO Validation)
    //--------------------------------------------------------------------------------------------------------------------

    @Override @NonNull
    protected ResponseEntity<Object>handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex, @NonNull HttpHeaders headers, @NonNull HttpStatus status, @NonNull WebRequest request)
    {
        List<FieldErrors> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
            .map(this::toFieldError)
            .collect(Collectors.toList());

        List<GlobalErrors> globalErrors = ex.getBindingResult().getGlobalErrors().stream()
            .map(this::toGlobalError)
            .collect(Collectors.toList());

        ApiError apiError = ApiError.builder()
            .status(HttpStatus.BAD_REQUEST)
            .shortMessage("Invalid URL parameter")
            .localizedMessage(ex.getLocalizedMessage())
            .fieldErrors(fieldErrors)
            .globalErrors(globalErrors)
            .build();

        return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
    }

    // Display the result of constaint violations (Entity Validation)
    //--------------------------------------------------------------------------------------------------------------------

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object>handleConstraintViolation(ConstraintViolationException ex, WebRequest request)
    {
        List<FieldErrors> fieldErrors = ex.getConstraintViolations().stream()
            .map(this::toFieldError)
            .collect(Collectors.toList());

        ApiError apiError = ApiError.builder()
            .status(HttpStatus.BAD_REQUEST)
            .shortMessage("Invalid Entity")
            .localizedMessage(ex.getLocalizedMessage())
            .fieldErrors(fieldErrors)
            .build();

        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    // Throw when missing parameter
    //--------------------------------------------------------------------------------------------------------------------

    @Override @NonNull
    protected ResponseEntity<Object>handleMissingServletRequestParameter(
        MissingServletRequestParameterException ex, @NonNull HttpHeaders headers, @NonNull HttpStatus status, @NonNull WebRequest request)
    {
        String error = String.format("Invalid URL: %s parameter is missing", ex.getParameterName());

        ApiError apiError = ApiError.builder()
            .status(HttpStatus.BAD_REQUEST)
            .shortMessage(error)
            .localizedMessage(ex.getLocalizedMessage())
            .build();

        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    // Argument is not the expected type
    //--------------------------------------------------------------------------------------------------------------------

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

    // FORBIDDEN:
    //--------------------------------------------------------------------------------------------------------------------

    // Access denied for the current user
    //--------------------------------------------------------------------------------------------------------------------

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<ApiError>handleUserDenied(AccessDeniedException ex, WebRequest request)
    {
        String error = String.format("Access denied to: %s, for the user: %s",
            ((ServletWebRequest)request).getRequest().getRequestURI(),
            request.getUserPrincipal() != null? request.getUserPrincipal().getName() : "current");

        ApiError apiError = ApiError.builder()
            .status(HttpStatus.FORBIDDEN)
            .shortMessage(error)
            .localizedMessage(ex.getLocalizedMessage())
            .build();

        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    // NOT FOUND REQUEST:
    //--------------------------------------------------------------------------------------------------------------------

    // Throw when url doesn't exist
    //--------------------------------------------------------------------------------------------------------------------

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
    //--------------------------------------------------------------------------------------------------------------------

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
    //--------------------------------------------------------------------------------------------------------------------

    private GlobalErrors toGlobalError(ObjectError error)
    {
        return GlobalErrors.builder()
            .type(error.getObjectName())
            .reason(error.getDefaultMessage())
            .build();
    }

    private FieldErrors toFieldError(FieldError error)
    {
        return FieldErrors.builder()
            .entityName(error.getObjectName())
            .fieldName(error.getField())
            .fieldValue(error.getRejectedValue() != null ? error.getRejectedValue().toString() : "")
            .reason(error.getDefaultMessage())
            .build();
    }

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