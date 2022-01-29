package com.disqo.notes.exception;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
 }
