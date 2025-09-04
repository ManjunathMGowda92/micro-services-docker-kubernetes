package org.fourstack.accounts.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.fourstack.accounts.dto.ErrorResponseDto;
import org.fourstack.accounts.util.ApplicationUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(CustomerAlreadyExistException.class)
    public ResponseEntity<ErrorResponseDto> handleException(CustomerAlreadyExistException exception, WebRequest request) {
        String apiPath = request.getDescription(false);

        ErrorResponseDto errorResponse = buildErrorResponse(exception.getMessage(), apiPath, HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleException(ResourceNotFoundException exception, WebRequest request) {
        String apiPath = request.getDescription(false);

        ErrorResponseDto errorResponse = buildErrorResponse(exception.getMessage(), apiPath, HttpStatus.NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ErrorResponseDto> handleException(InvalidInputException exception, WebRequest request) {
        String apiPath = request.getDescription(false);

        ErrorResponseDto errorResponse = buildErrorResponse(exception.getMessage(), apiPath, HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleException(MethodArgumentNotValidException exception, WebRequest request) {
        String apiPath = request.getDescription(false);

        BindingResult bindingResult = exception.getBindingResult();
        Map<String, List<String>> errorMap = bindingResult.getFieldErrors()
                .stream()
                .map(error -> Map.entry(error.getObjectName(),
                        ApplicationUtil.convertToString(generateFieldError(error.getDefaultMessage(),
                                error.getField(), error.getRejectedValue().toString()))))
                .collect(Collectors.toMap(Map.Entry::getKey,
                        entry -> {
                            List<String> errors = new ArrayList<>();
                            errors.add(entry.getValue());
                            return errors;
                        },
                        (oldEntries, newEntries) -> {
                            oldEntries.addAll(newEntries);
                            return oldEntries;
                        }));

        ErrorResponseDto errorResponse = buildErrorResponse(errorMap.toString(), apiPath, HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDto> handleException(ConstraintViolationException exception, WebRequest request) {
        String apiPath = request.getDescription(false);

        Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();
        List<FieldValidationError> errorList = constraintViolations.stream()
                .map(error ->
                        generateFieldError(error.getMessage(), error.getPropertyPath().toString(),
                                error.getInvalidValue().toString())
                ).toList();
        ErrorResponseDto errorResponse = buildErrorResponse("Constraints Violated : " + errorList,
                apiPath, HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    private static FieldValidationError generateFieldError(String fieldError, String fieldName, String rejectedValue) {
        return FieldValidationError.builder().fieldError(fieldError)
                .fieldName(fieldName)
                .rejectedValue(rejectedValue)
                .build();
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleException(NoResourceFoundException exception, WebRequest request) {
        String apiPath = request.getDescription(false);

        ErrorResponseDto errorResponse = buildErrorResponse("URL not mapped : " + exception.getMessage(), apiPath, HttpStatus.NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleException(Exception exception, WebRequest request) {
        String apiPath = request.getDescription(false);

        ErrorResponseDto errorResponse = buildErrorResponse(exception.getMessage(), apiPath, HttpStatus.INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
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
