package org.fourstack.accounts.exception;

import org.fourstack.accounts.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomerAlreadyExistException.class)
    public ResponseEntity<ErrorResponseDto> handleException(CustomerAlreadyExistException exception, WebRequest request) {
        String apiPath = request.getDescription(false);

        ErrorResponseDto errorResponse= buildErrorResponse(exception.getMessage(), apiPath, HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleException(ResourceNotFoundException exception, WebRequest request) {
        String apiPath = request.getDescription(false);

        ErrorResponseDto errorResponse= buildErrorResponse(exception.getMessage(), apiPath, HttpStatus.NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ErrorResponseDto> handleException(InvalidInputException exception, WebRequest request) {
        String apiPath = request.getDescription(false);

        ErrorResponseDto errorResponse= buildErrorResponse(exception.getMessage(), apiPath, HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    private ErrorResponseDto buildErrorResponse(String message, String path, HttpStatus status) {
        path = path.contains("uri=") ? path.substring(path.indexOf("uri=") + 4) : path;
        return ErrorResponseDto.builder()
                .apiPath(path)
                .errorMessage(message)
                .statusCode(status.value())
                .status(status)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
