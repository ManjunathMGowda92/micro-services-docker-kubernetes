package org.fourstack.accounts.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDetailsDto {
    private CustomerDto customerInfo;
    private AccountsDto accountInfo;
}
