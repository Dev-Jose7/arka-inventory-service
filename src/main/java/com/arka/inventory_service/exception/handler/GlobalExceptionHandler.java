package com.arka.inventory_service.exception.handler;

import com.arka.inventory_service.dto.response.ErrorResponseDTO;
import com.arka.inventory_service.exception.EmailSendException;
import com.arka.inventory_service.exception.InvalidTypeException;
import com.arka.inventory_service.exception.ResourceAlreadyExistsException;
import com.arka.inventory_service.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleResourceNotFound(ResourceNotFoundException ex,
                                                                    HttpServletRequest request) {
        return createResponse(ex, HttpStatus.NOT_FOUND, request.getRequestURI());
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDTO> handleResourceAlreadyExists(ResourceAlreadyExistsException ex,
                                                                            HttpServletRequest request) {
        return createResponse(ex, HttpStatus.CONFLICT, request.getRequestURI());
    }

    @ExceptionHandler(InvalidTypeException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidType(InvalidTypeException ex,
                                                                 HttpServletRequest request) {
        return createResponse(ex, HttpStatus.CONFLICT, request.getRequestURI());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseDTO> handleDataIntegrityViolation(DataIntegrityViolationException ex,
                                                                         HttpServletRequest request) {
        return createResponse(
                new ResourceAlreadyExistsException("Data already exists or violates a database constraint."),
                HttpStatus.CONFLICT,
                request.getRequestURI()
        );
    }

    @ExceptionHandler(EmailSendException.class)
    public ResponseEntity<ErrorResponseDTO> handleEmailSend(EmailSendException ex,
                                                            HttpServletRequest request) {
        return createResponse(ex, HttpStatus.CONFLICT, request.getRequestURI());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handlerException(Exception ex,
                                                             HttpServletRequest request) {
        return createResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR, request.getRequestURI());
    }

    private ResponseEntity<ErrorResponseDTO> createResponse(Exception ex,
                                                            HttpStatus status,
                                                            String path) {
        ErrorResponseDTO response = new ErrorResponseDTO(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                ex.getMessage(),
                path
        );

        return new ResponseEntity<>(response, status);
    }
}
