package org.fourstack.accounts.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountsDto {
    private Long accountNumber;
    private String accountType;
    private String branchAddress;
}
