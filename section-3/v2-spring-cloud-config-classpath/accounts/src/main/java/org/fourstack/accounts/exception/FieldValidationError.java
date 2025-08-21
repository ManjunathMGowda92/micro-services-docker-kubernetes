package org.fourstack.accounts.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FieldValidationError {
    private String fieldName;
    private String rejectedValue;
    private String fieldError;

    @Override
    public String toString() {
        return "{ \"fieldName\" : %s, \"rejectedValue\" : %s,\"fieldError\" : %s}"
                .formatted(this.fieldName, this.rejectedValue, this.fieldError);
    }
}
