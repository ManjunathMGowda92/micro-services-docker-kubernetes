package org.fourstack.accounts.controller;

import lombok.RequiredArgsConstructor;
import org.fourstack.accounts.dto.CustomerAccountRequestDto;
import org.fourstack.accounts.dto.CustomerDetailsDto;
import org.fourstack.accounts.dto.CustomerDto;
import org.fourstack.accounts.dto.ResponseDto;
import org.fourstack.accounts.service.AccountsService;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/accounts", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor(onConstructor_ = @Lazy)
public class AccountsController {
    private final AccountsService accountsService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@RequestBody CustomerDto customerDto) {
        return accountsService.createAccount(customerDto);
    }

    @GetMapping("/fetch-by-mob-num/{mobileNumber}")
    public ResponseEntity<CustomerDto> retrieveDetailsByMobileNumber(@PathVariable String mobileNumber) {
        return accountsService.retrieveCustomerByMobileNumber(mobileNumber);
    }

    @GetMapping("/fetch-by-email/{email}")
    public ResponseEntity<CustomerDto> retrieveDetailsByEmail(@PathVariable String email) {
        return accountsService.retrieveCustomerByEmail(email);
    }

    @PostMapping("/fetch-customer")
    public ResponseEntity<CustomerDetailsDto> retrieveCompleteCustomerDetailsByMobile(
            @RequestBody CustomerAccountRequestDto dto) {
        return accountsService.retrieveCompleteCustomerDetails(dto);
    }

    @PutMapping("/{accountNumber}")
    public ResponseEntity<ResponseDto> updateAccountDetails(@RequestBody CustomerDetailsDto dto,
                                                            @PathVariable long accountNumber) {
        return accountsService.updateAccount(dto, accountNumber);
    }
}
