package org.fourstack.accounts.service;

import org.fourstack.accounts.dto.CustomerDto;
import org.fourstack.accounts.dto.ResponseDto;
import org.springframework.http.ResponseEntity;

public interface AccountsService {
    /**
     * Method to create the account for the Customer.
     *
     * @param dto CustomerDto object.
     * @return ResponseDto by indicating status with account created or not.
     */
    ResponseEntity<ResponseDto> createAccount(CustomerDto dto);
}
