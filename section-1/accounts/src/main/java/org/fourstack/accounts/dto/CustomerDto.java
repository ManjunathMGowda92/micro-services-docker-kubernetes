package org.fourstack.accounts.dto;

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
public class CustomerDto {
    @NotEmpty(message = "Name cannot be empty or null")
    @Size(min = 5, max = 50, message = "Length of name should be between 5 to 50 characters")
    private String name;

    @NotEmpty(message = "Email address cannot be empty or null")
    @Email(message = "Invalid email value received")
    private String email;

    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
    private String mobileNumber;
}
