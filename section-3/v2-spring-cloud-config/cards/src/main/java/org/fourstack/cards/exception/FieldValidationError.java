package org.fourstack.cards.exception;


public record FieldValidationError(String fieldName, String rejectedValue, String fieldError) {
}
