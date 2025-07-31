package org.fourstack.accounts.service;

import lombok.RequiredArgsConstructor;
import org.fourstack.accounts.constants.AccountsConstants;
import org.fourstack.accounts.dto.CustomerAccountRequestDto;
import org.fourstack.accounts.dto.CustomerDetailsDto;
import org.fourstack.accounts.dto.CustomerDto;
import org.fourstack.accounts.dto.ResponseDto;
import org.fourstack.accounts.entity.Accounts;
import org.fourstack.accounts.entity.Customer;
import org.fourstack.accounts.exception.ResourceNotFoundException;
import org.fourstack.accounts.mapper.AccountsMapper;
import org.fourstack.accounts.mapper.CustomerMapper;
import org.fourstack.accounts.repository.AccountsRepository;
import org.fourstack.accounts.repository.CustomerRepository;
import org.fourstack.accounts.util.ApplicationUtil;
import org.fourstack.accounts.validation.CustomerValidator;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor_ = @Lazy)
public class AccountsServiceImpl implements AccountsService {
    private final AccountsRepository accountsRepository;
    private final CustomerRepository customerRepository;
    private final AccountsMapper accountsMapper;
    private final CustomerMapper customerMapper;
    private final CustomerValidator customerValidator;

    /**
     * Method to create the account for the Customer.
     *
     * @param dto CustomerDto object.
     * @return ResponseDto by indicating status with account created or not.
     */
    @Override
    public ResponseEntity<ResponseDto> createAccount(CustomerDto dto) {
        // Validate the Customer exist by Mobile Number or Email
        customerValidator.validateForNewAccountCreation(dto);

        // Convert CustomerDTO to Customer Entity
        Customer customer = customerMapper.mapToCustomer(dto);
        customer.setCreatedBy("Anonymous");
        customer.setCreationTimestamp(LocalDateTime.now());

        // Save the Customer to Database.
        Customer savedCustomer = customerRepository.save(customer);

        // Create an Account for the saved Customer.
        accountsRepository.save(createNewAccount(savedCustomer));

        // Return the Success Response
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(HttpStatus.CREATED.value(),
                        HttpStatus.CREATED, AccountsConstants.ACCOUNT_CREATED));
    }

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
     * Retrieves the Customer object by provided mobile number or throws exception.
     *
     * @param mobileNumber Input mobile number.
     * @return Customer object retrieved.
     * @throws ResourceNotFoundException if the Customer entity not exist with given mobile number.
     */
    private Customer retrieveCustomerByMobile(String mobileNumber) {
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(mobileNumber);
        return optionalCustomer.orElseThrow(() ->
                new ResourceNotFoundException("Customer", "Mobile Number", mobileNumber));
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

    /**
     * Method to retrieve the Complete Customer details including account information by mobile number.
     *
     * @param mobileNumber Input mobile number.
     * @return CustomerDetailsDto object associated with mobile number or error response.
     */
    @Override
    public ResponseEntity<CustomerDetailsDto> retrieveCustomerDetailsByMobileNumber(String mobileNumber) {
        Customer customer = retrieveCustomerByMobile(mobileNumber);
        Optional<Accounts> optionalAccounts = retrieveAccount(customer.getCustomerId());
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
        Optional<Accounts> optionalAccounts = retrieveAccount(customer.getCustomerId());
        Accounts accounts = optionalAccounts.orElseThrow(() ->
                new ResourceNotFoundException("Accounts", "Email ID", email));
        return ResponseEntity.status(HttpStatus.OK)
                .body(CustomerDetailsDto.builder()
                        .customerInfo(customerMapper.mapToCustomerDto(customer))
                        .accountInfo(accountsMapper.mapToAccountsDto(accounts))
                        .build());
    }

    /**
     * Method to retrieve the Accounts Entity using the CustomerId.
     *
     * @param customerId CustomerId of a customer.
     * @return Accounts Entity object.
     */
    @Override
    public Optional<Accounts> retrieveAccount(long customerId) {
        return accountsRepository.findByCustomerId(customerId);
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
     * Method to create a new account for a given cutsomer.
     *
     * @param customer Customer Object.
     * @return New Accounts Object.
     */
    private Accounts createNewAccount(Customer customer) {
        Accounts accounts = new Accounts();
        accounts.setCustomerId(customer.getCustomerId());
        accounts.setAccountNumber(ApplicationUtil.generateAccountNumber());
        accounts.setAccountType(AccountsConstants.SAVINGS_ACCOUNT);
        accounts.setBranchAddress(AccountsConstants.BRANCH_ADDRESS);

        accounts.setCreatedBy("Anonymous");
        accounts.setCreationTimestamp(LocalDateTime.now());
        return accounts;
    }
}
