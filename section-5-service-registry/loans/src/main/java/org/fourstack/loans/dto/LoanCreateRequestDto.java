package org.fourstack.loans.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Schema(name = "Loan Request", description = "Schema to hold new loan request information")
@Data
public class LoanCreateRequestDto {
    @NotEmpty(message = "Mobile Number can not be a null or empty")
    @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile Number must be 10 digits")
    @Schema(description = "Mobile Number of Customer", example = "4365327698")
    private String mobileNumber;

    @NotEmpty(message = "LoanType can not be a null or empty")
    @Schema(description = "Type of the loan", example = "Home Loan")
    private String loanType;

    @Positive(message = "Total loan amount should be greater than zero")
    @Schema(description = "Total loan amount", example = "100000")
    private int totalLoanAmount;
}
