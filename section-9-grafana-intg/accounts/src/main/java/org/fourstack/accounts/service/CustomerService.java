package org.fourstack.accounts.service;

import org.fourstack.accounts.dto.CompleteCustomerDetailsDto;
import org.fourstack.accounts.dto.CustomerAccountRequestDto;
import org.fourstack.accounts.dto.CustomerDetailsDto;
import org.fourstack.accounts.dto.CustomerDto;
import org.fourstack.accounts.entity.Customer;
import org.fourstack.accounts.exception.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;

public interface CustomerService {

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
     * Retrieves the Customer object by provided mobile number or throws exception.
     *
     * @param mobileNumber Input mobile number.
     * @return Customer object retrieved.
     * @throws ResourceNotFoundException if the Customer entity not exist with given mobile number.
     */
    Customer retrieveCustomerByMobile(String mobileNumber);

    /**
     * Method to retrieve the Customer and Account Information using Email or Mobile Number.
     * Priority will be provided for Mobile Number, if mobile number not populated then details will be
     * fetched by using Email ID.
     *
     * @param dto CustomerAccountRequestDto object enclosing mobile number and email.
     * @return CustomerDetailsDto object including customer and Accounts information.
     */
    ResponseEntity<CustomerDetailsDto> retrieveCompleteCustomerDetails(CustomerAccountRequestDto dto);

    /**
     * Method to retrieve the complete Customer Information by using mobile number. This will include customer,
     * account, loans and cards details.
     *
     * @param mobileNumber Input mobile number value.
     * @return Complete Customer details object.
     */
    ResponseEntity<CompleteCustomerDetailsDto> retrieveCompleteCustomerInfo(String mobileNumber);

}
