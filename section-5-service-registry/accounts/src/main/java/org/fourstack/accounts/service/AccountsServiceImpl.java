package org.fourstack.accounts.service;

import lombok.RequiredArgsConstructor;
import org.fourstack.accounts.constants.AccountsConstants;
import org.fourstack.accounts.dto.AccountsDto;
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
    private final CustomerService customerService;
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
        return buildResponseEntity(HttpStatus.CREATED, AccountsConstants.ACCOUNT_CREATED);
    }

    /**
     * Method to update the customer and account details.
     *
     * @param dto           Customer Update Request DTO object.
     * @param accountNumber Account Number for which update is required.
     * @return ResponseDto object with status updated or not.
     */
    @Override
    public ResponseEntity<ResponseDto> updateAccount(CustomerDetailsDto dto, long accountNumber) {
        AccountsDto accountInfo = dto.getAccountInfo();
        if (Objects.nonNull(accountInfo)) {
            Accounts accounts = accountsRepository.findById(accountNumber)
                    .orElseThrow(() -> new ResourceNotFoundException("Account", "Account Number", accountNumber + ""));
            accountsMapper.mapToAccounts(accountInfo, accounts);
            accountsRepository.save(accounts);

            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId)
                    .orElseThrow(() -> new ResourceNotFoundException("Customer", "CustomerID", customerId + ""));
            customerMapper.mapToCustomer(dto.getCustomerInfo(), customer);
            customerRepository.save(customer);
            return buildResponseEntity(HttpStatus.OK, AccountsConstants.RECORDS_UPDATED);
        }
        return buildResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY, AccountsConstants.INSUFFICIENT_DATA);
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
     * Method to delete the account information based on the input mobile number.
     *
     * @param mobileNumber Input Mobile number value.
     * @return ResponseDto with status to indicate the account deleted or not.
     */
    @Override
    public ResponseEntity<ResponseDto> deleteAccount(String mobileNumber) {
        Customer customer = customerService.retrieveCustomerByMobile(mobileNumber);
        if (Objects.nonNull(customer)) {
            // delete the account by using CustomerID.
            accountsRepository.deleteByCustomerId(customer.getCustomerId());

            // delete the customer entity by ID
            customerRepository.deleteById(customer.getCustomerId());

            return buildResponseEntity(HttpStatus.OK, AccountsConstants.DELETION_SUCCESS);
        }
        return buildResponseEntity(HttpStatus.NOT_FOUND, AccountsConstants.RECORD_NOT_FOUND);
    }


    /**
     * Method to create a new account for a given Customer.
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

    private ResponseEntity<ResponseDto> buildResponseEntity(HttpStatus status, String statusMsg) {
        return ResponseEntity.status(status)
                .body(ResponseDto.builder()
                        .status(status)
                        .statusCode(status.value())
                        .statusMsg(statusMsg)
                        .build());
    }
}
