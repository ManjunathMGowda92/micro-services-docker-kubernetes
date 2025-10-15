package org.fourstack.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "Accounts", description = "Schema to hold Accounts information")
public class AccountsDto {
    @Schema(description = "Account number value")
    private Long accountNumber;

    @Schema(description = "Account type", examples = {"Savings", "Current"})
    @NotEmpty(message = "Account Type cannot be null or empty")
    private String accountType;

    @Schema(description = "Branch address details", example = "#121, 4th Street, 11th Main, Bellanduru - 560102")
    @NotEmpty(message = "Branch Address cannot be null or empty")
    private String branchAddress;
}
