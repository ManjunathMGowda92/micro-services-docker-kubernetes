package org.fourstack.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "Customer", description = "Schema to hold Customer information")
public class CustomerDto {
    @Schema(description = "Name of the customer", example = "FourStack Developer")
    @NotEmpty(message = "Name cannot be empty or null")
    @Size(min = 5, max = 50, message = "Length of name should be between 5 to 50 characters")
    private String name;

    @Schema(description = "Email value of the Customer", example = "fourstackdev@gmail.com")
    @NotEmpty(message = "Email address cannot be empty or null")
    @Email(message = "Invalid email value received")
    private String email;

    @Schema(description = "Mobile number of the Customer", example = "9876543210")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
    private String mobileNumber;
}
