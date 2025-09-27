package org.fourstack.accounts.mapper;

import org.fourstack.accounts.dto.AccountsDto;
import org.fourstack.accounts.entity.Accounts;
import org.springframework.stereotype.Component;

@Component
public class AccountsMapper {

    public AccountsDto mapToAccountsDto(Accounts accounts) {
        return AccountsDto.builder()
                .accountNumber(accounts.getAccountNumber())
                .accountType(accounts.getAccountType())
                .branchAddress(accounts.getBranchAddress())
                .build();
    }

    public Accounts mapToAccounts(AccountsDto dto) {
        Accounts accounts = new Accounts();
        accounts.setAccountNumber(dto.getAccountNumber());
        accounts.setAccountType(dto.getAccountType());
        accounts.setBranchAddress(dto.getBranchAddress());
        return accounts;
    }

    public void mapToAccounts(AccountsDto dto, Accounts accounts) {
        accounts.setAccountType(dto.getAccountType());
        accounts.setBranchAddress(dto.getBranchAddress());
    }
}
