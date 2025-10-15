package org.fourstack.accounts.service;

import org.fourstack.accounts.dto.CustomerDetailsDto;
import org.fourstack.accounts.dto.CustomerDto;
import org.fourstack.accounts.dto.ResponseDto;
import org.fourstack.accounts.entity.Accounts;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface AccountsService {
    /**
     * Method to create the account for the Customer.
     *
     * @param dto CustomerDto object.
     * @return ResponseDto by indicating status with account created or not.
     */
    ResponseEntity<ResponseDto> createAccount(CustomerDto dto);

    /**
     * Method to update the customer and account details.
     *
     * @param dto           Customer Update Request DTO object.
     * @param accountNumber Account Number for which update is required.
     * @return ResponseDto object with status updated or not.
     */
    ResponseEntity<ResponseDto> updateAccount(CustomerDetailsDto dto, long accountNumber);

    /**
     * Method to retrieve the Accounts Entity using the CustomerId.
     *
     * @param customerId CustomerId of a customer.
     * @return Accounts Entity object.
     */
    Optional<Accounts> retrieveAccount(long customerId);

    /**
     * Method to delete the account information based on the input mobile number.
     *
     * @param mobileNumber Input Mobile number value.
     * @return ResponseDto with status to indicate the account deleted or not.
     */
    ResponseEntity<ResponseDto> deleteAccount(String mobileNumber);
}
