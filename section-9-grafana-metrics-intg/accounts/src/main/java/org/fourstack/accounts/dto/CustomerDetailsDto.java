package org.fourstack.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "Customer-Account Details", description = "Schema to hold Customer and Accounts information")
public class CustomerDetailsDto {
    private CustomerDto customerInfo;
    private AccountsDto accountInfo;
}