package org.fourstack.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        name = "Customer-Accounts-Request",
        description = "Schema to fetch Customer and Accounts Info by Mobile or Email"
)
public class CustomerAccountRequestDto {
    @Schema(description = "Email Value", example = "fourstackdev@gmail.com")
    @Email(message = "Invalid email value received")
    private String email;

    @Schema(description = "Mobile number value", example = "9876543210")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
    private String mobileNumber;
}
