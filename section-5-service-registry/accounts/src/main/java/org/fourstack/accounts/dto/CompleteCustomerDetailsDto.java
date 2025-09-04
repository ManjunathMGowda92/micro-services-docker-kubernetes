package org.fourstack.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "CustomerDetails", description = "Schema to hold Customer, Accounts, Loans and Cards information")
public class CompleteCustomerDetailsDto {
    private CustomerDto customerInfo;
    private AccountsDto accountInfo;
    private List<LoansDto> loans;
    private List<CardsDto> cards;
}
