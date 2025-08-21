package org.fourstack.loans.exception;


public record FieldValidationError(String fieldName,
                                   String rejectedValue,
                                   String fieldError) {
}
