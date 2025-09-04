package org.fourstack.accounts.service;

import lombok.RequiredArgsConstructor;
import org.fourstack.accounts.dto.CardsDto;
import org.fourstack.accounts.dto.CompleteCustomerDetailsDto;
import org.fourstack.accounts.dto.CustomerAccountRequestDto;
import org.fourstack.accounts.dto.CustomerDetailsDto;
import org.fourstack.accounts.dto.CustomerDto;
import org.fourstack.accounts.dto.LoansDto;
import org.fourstack.accounts.entity.Accounts;
import org.fourstack.accounts.entity.Customer;
import org.fourstack.accounts.exception.ResourceNotFoundException;
import org.fourstack.accounts.helper.CardsFeignClient;
import org.fourstack.accounts.helper.LoansFeignClient;
import org.fourstack.accounts.mapper.AccountsMapper;
import org.fourstack.accounts.mapper.CustomerMapper;
import org.fourstack.accounts.repository.CustomerRepository;
import org.fourstack.accounts.validation.CustomerValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService{
    private final AccountsService accountsService;
    private final CustomerValidator customerValidator;
    private final CustomerRepository customerRepository;
    private final AccountsMapper accountsMapper;
    private final CustomerMapper customerMapper;
    private final CardsFeignClient cardsFeignClient;
    private final LoansFeignClient loansFeignClient;

    /**
     * Method to retrieve the Customer details by mobile number provided.
     *
     * @param mobileNumber Input mobile number.
     * @return CustomerDto object is mobile number exists or error response.
     */
    @Override
    public ResponseEntity<CustomerDto> retrieveCustomerByMobileNumber(String mobileNumber) {
        Customer customer = retrieveCustomerByMobile(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK)
                .body(customerMapper.mapToCustomerDto(customer));
    }

    /**
     * Method to retrieve the Customer details by email ID  provided.
     *
     * @param email Input email ID.
     * @return CustomerDto object is email ID exists or error response.
     */
    @Override
    public ResponseEntity<CustomerDto> retrieveCustomerByEmail(String email) {
        Customer customer = retrieveCustomerByEmailId(email);
        return ResponseEntity.status(HttpStatus.OK)
                .body(customerMapper.mapToCustomerDto(customer));
    }

    /**
     * Method to retrieve the Complete Customer details including account information by mobile number.
     *
     * @param mobileNumber Input mobile number.
     * @return CustomerDetailsDto object associated with mobile number or error response.
     */
    @Override
    public ResponseEntity<CustomerDetailsDto> retrieveCustomerDetailsByMobileNumber(String mobileNumber) {
        Customer customer = retrieveCustomerByMobile(mobileNumber);
        Optional<Accounts> optionalAccounts = accountsService.retrieveAccount(customer.getCustomerId());
        Accounts accounts = optionalAccounts.orElseThrow(() ->
                new ResourceNotFoundException("Accounts", "Mobile Number", mobileNumber));
        return ResponseEntity.status(HttpStatus.OK)
                .body(CustomerDetailsDto.builder()
                        .customerInfo(customerMapper.mapToCustomerDto(customer))
                        .accountInfo(accountsMapper.mapToAccountsDto(accounts))
                        .build());
    }

    /**
     * Method to retrieve the Complete Customer details including account information by Email ID.
     *
     * @param email Input Email ID value.
     * @return CustomerDetailsDto object associated with Email ID or error response.
     */
    @Override
    public ResponseEntity<CustomerDetailsDto> retrieveCustomerDetailsByEmail(String email) {
        Customer customer = retrieveCustomerByEmailId(email);
        Optional<Accounts> optionalAccounts = accountsService.retrieveAccount(customer.getCustomerId());
        Accounts accounts = optionalAccounts.orElseThrow(() ->
                new ResourceNotFoundException("Accounts", "Email ID", email));
        return ResponseEntity.status(HttpStatus.OK)
                .body(CustomerDetailsDto.builder()
                        .customerInfo(customerMapper.mapToCustomerDto(customer))
                        .accountInfo(accountsMapper.mapToAccountsDto(accounts))
                        .build());
    }

    /**
     * Method to retrieve the Customer and Account Information using Email or Mobile Number.
     * Priority will be provided for Mobile Number, if mobile number not populated then details will be
     * fetched by using Email ID.
     *
     * @param dto CustomerAccountRequestDto object enclosing mobile number and email.
     * @return CustomerDetailsDto object including customer and Accounts information.
     */
    @Override
    public ResponseEntity<CustomerDetailsDto> retrieveCompleteCustomerDetails(CustomerAccountRequestDto dto) {
        customerValidator.validateCustomerAccountRequestDto(dto);
        String mobileNumber = dto.getMobileNumber();
        if (Objects.nonNull(mobileNumber) && !mobileNumber.isBlank()) {
            return retrieveCustomerDetailsByMobileNumber(mobileNumber);
        } else {
            return retrieveCustomerDetailsByEmail(dto.getEmail());
        }
    }

    /**
     * Method to retrieve the complete Customer Information by using mobile number. This will include customer,
     * account, loans and cards details.
     *
     * @param mobileNumber Input mobile number value.
     * @return Complete Customer details object.
     */
    @Override
    public ResponseEntity<CompleteCustomerDetailsDto> retrieveCompleteCustomerInfo(String mobileNumber) {
        Customer customer = retrieveCustomerByMobile(mobileNumber);
        Optional<Accounts> optionalAccounts = accountsService.retrieveAccount(customer.getCustomerId());
        Accounts accounts = optionalAccounts.orElseThrow(() ->
                new ResourceNotFoundException("Accounts", "Mobile Number", mobileNumber));

        ResponseEntity<List<CardsDto>> cardsResponse = cardsFeignClient.retrieveCards(mobileNumber);
        ResponseEntity<List<LoansDto>> loansResponse = loansFeignClient.retrieveLoan(mobileNumber);
        CompleteCustomerDetailsDto dto = new CompleteCustomerDetailsDto();
        dto.setCustomerInfo(customerMapper.mapToCustomerDto(customer));
        dto.setAccountInfo(accountsMapper.mapToAccountsDto(accounts));
        if (cardsResponse.getStatusCode().is2xxSuccessful()) {
            dto.setCards(cardsResponse.getBody());
        }

        if (loansResponse.getStatusCode().is2xxSuccessful()) {
            dto.setLoans(loansResponse.getBody());
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(dto);
    }

    /**
     * Retrieves the Customer object by provided mobile number or throws exception.
     *
     * @param mobileNumber Input mobile number.
     * @return Customer object retrieved.
     * @throws ResourceNotFoundException if the Customer entity not exist with given mobile number.
     */
    @Override
    public Customer retrieveCustomerByMobile(String mobileNumber) {
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(mobileNumber);
        return optionalCustomer.orElseThrow(() ->
                new ResourceNotFoundException("Customer", "Mobile Number", mobileNumber));
    }

    /**
     * Retrieves the Customer object by provided Email ID or throws exception.
     *
     * @param email Input Email ID.
     * @return Customer object retrieved.
     * @throws ResourceNotFoundException if the Customer entity not exist with given Email ID.
     */
    private Customer retrieveCustomerByEmailId(String email) {
        Optional<Customer> optionalCustomer = customerRepository.findByEmail(email);
        return optionalCustomer.orElseThrow(() ->
                new ResourceNotFoundException("Customer", "Email", email));
    }
}
