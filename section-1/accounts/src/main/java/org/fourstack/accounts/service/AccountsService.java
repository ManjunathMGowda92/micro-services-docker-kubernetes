package org.fourstack.accounts.service;

import org.fourstack.accounts.dto.CustomerAccountRequestDto;
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
     * Method to retrieve the Customer details by mobile number provided.
     *
     * @param mobileNumber Input mobile number.
     * @return CustomerDto object is mobile number exists or error response.
     */
    ResponseEntity<CustomerDto> retrieveCustomerByMobileNumber(String mobileNumber);

    /**
     * Method to retrieve the Customer details by email ID  provided.
     *
     * @param email Input email ID.
     * @return CustomerDto object is email ID exists or error response.
     */
    ResponseEntity<CustomerDto> retrieveCustomerByEmail(String email);

    /**
     * Method to retrieve the Complete Customer details including account information by mobile number.
     *
     * @param mobileNumber Input mobile number.
     * @return CustomerDetailsDto object associated with mobile number or error response.
     */
    ResponseEntity<CustomerDetailsDto> retrieveCustomerDetailsByMobileNumber(String mobileNumber);

    /**
     * Method to retrieve the Complete Customer details including account information by Email ID.
     *
     * @param email Input Email ID value.
     * @return CustomerDetailsDto object associated with Email ID or error response.
     */
    ResponseEntity<CustomerDetailsDto> retrieveCustomerDetailsByEmail(String email);

    /**
     * Method to retrieve the Accounts Entity using the CustomerId.
     *
     * @param customerId CustomerId of a customer.
     * @return Accounts Entity object.
     */
    Optional<Accounts> retrieveAccount(long customerId);

    /**
     * Method to retrieve the Customer and Account Information using Email or Mobile Number.
     * Priority will be provided for Mobile Number, if mobile number not populated then details will be
     * fetched by using Email ID.
     *
     * @param dto CustomerAccountRequestDto object enclosing mobile number and email.
     * @return CustomerDetailsDto object including customer and Accounts information.
     */
    ResponseEntity<CustomerDetailsDto> retrieveCompleteCustomerDetails(CustomerAccountRequestDto dto);
}
