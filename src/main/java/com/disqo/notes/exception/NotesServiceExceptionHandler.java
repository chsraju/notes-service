package com.disqo.notes.exception;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException.BadRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class NotesServiceExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(BadRequest.class)
    public ResponseEntity<NotesErrorResponse> handleBadRequest(BadRequest ex, WebRequest request) {
        logger.error("handleBadRequest: ", ex);
        String error = ex.getMessage();

        NotesErrorResponse exResponse = new NotesErrorResponse(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
        return new ResponseEntity<>(exResponse, new HttpHeaders(), exResponse.getStatus());
    }

    @ExceptionHandler({ DataIntegrityViolationException.class })
    public ResponseEntity<NotesErrorResponse> handleResourceNotFoundException(
            DataIntegrityViolationException ex, WebRequest request)
    {
        logger.error("Invalid Input: ", ex);
        Throwable t = ex.getCause();
        List<String> errors = new ArrayList<>();

        if (t instanceof ConstraintViolationException) {

            errors.add(((ConstraintViolationException) t).getConstraintName());
            errors.add(((ConstraintViolationException) t).getSQLException().getMessage());

        } else {

            errors.add(ex.getMessage());
        }

        NotesErrorResponse exResponse = new NotesErrorResponse(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(),
                errors);
        return new ResponseEntity<>(exResponse, new HttpHeaders(), exResponse.getStatus());
    }

    /*
     * MissingServletRequestParameterException: When Required method parameter
     * is NOT passed
     */
    @Override
    public ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request)
    {
        logger.error("handleMissingServletRequestParameter: ", ex);
        String error = ex.getParameterName() + " parameter is missing";

        NotesErrorResponse exResponse = new NotesErrorResponse(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
        return handleExceptionInternal(ex, exResponse, headers, exResponse.getStatus(), request);

    }

    @ExceptionHandler(value = { ResourceNotFoundException.class })
    public ResponseEntity<NotesErrorResponse> updateNotFound(RuntimeException ex, WebRequest request) {
        logger.error("Note note found: ", ex);
        String error = ex.getMessage();

        NotesErrorResponse exResponse = new NotesErrorResponse(HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), error);
        return new ResponseEntity<>(exResponse, new HttpHeaders(), exResponse.getStatus());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<NotesErrorResponse> invalidInput(MethodArgumentTypeMismatchException ex, WebRequest request) {
        logger.error("invalidInput: ", ex);
        String error = ex.getName() + " should be of type " + ex.getRequiredType().getName();

        NotesErrorResponse exResponse = new NotesErrorResponse(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
        return new ResponseEntity<>(exResponse, new HttpHeaders(), exResponse.getStatus());
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
        logger.error("handleAll: ", ex);

        NotesErrorResponse exResponse = new NotesErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getLocalizedMessage(), ex.getClass().getSimpleName());
        return new ResponseEntity<>(exResponse, new HttpHeaders(), exResponse.getStatus());
    }

    /*
     * This method will be called when @Valid annotation is used at the Request
     * Parameter
     */
    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,HttpHeaders headers, HttpStatus status, WebRequest request)
    {
        logger.error("handleMethodArgumentNotValid: ", ex);

        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }

        NotesErrorResponse exResponse = new NotesErrorResponse(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(),
                errors);
        return handleExceptionInternal(ex, exResponse, headers, exResponse.getStatus(), request);
    }
 }
