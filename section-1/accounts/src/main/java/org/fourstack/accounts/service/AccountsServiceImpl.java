package org.fourstack.accounts.service;

import lombok.RequiredArgsConstructor;
import org.fourstack.accounts.constants.AccountsConstants;
import org.fourstack.accounts.dto.CustomerDto;
import org.fourstack.accounts.dto.ResponseDto;
import org.fourstack.accounts.entity.Accounts;
import org.fourstack.accounts.entity.Customer;
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
