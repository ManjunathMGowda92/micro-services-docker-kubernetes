package org.fourstack.accounts.validation;

import lombok.RequiredArgsConstructor;
import org.fourstack.accounts.dto.CustomerAccountRequestDto;
import org.fourstack.accounts.dto.CustomerDto;
import org.fourstack.accounts.exception.CustomerAlreadyExistException;
import org.fourstack.accounts.exception.InvalidInputException;
import org.fourstack.accounts.exception.ResourceNotFoundException;
import org.fourstack.accounts.repository.CustomerRepository;
import org.fourstack.accounts.util.ApplicationUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor(onConstructor_ = @Lazy)
public class CustomerValidator {
    private final CustomerRepository customerRepository;

    public void validateForNewAccountCreation(CustomerDto dto) {
        boolean existsByMobileNumber = customerRepository.existsByMobileNumber(dto.getMobileNumber());
        if (existsByMobileNumber) {
            throw new CustomerAlreadyExistException("Customer already exist for the given mobile number");
        }

        boolean existsByEmail = customerRepository.existsByEmail(dto.getEmail());
        if (existsByEmail) {
            throw new CustomerAlreadyExistException("Customer already exist for the given email ID");
        }
    }

    public void checkIsCustomerExistByMobileNumber(String mobileNumber) {
        boolean existsByMobileNumber = customerRepository.existsByMobileNumber(mobileNumber);
        if (!existsByMobileNumber) {
            throw new ResourceNotFoundException("Customer", "Mobile Number", mobileNumber);
        }
    }

    public void checkIsCustomerExistByEmail(String email) {
        boolean existsByEmail = customerRepository.existsByEmail(email);
        if (!existsByEmail) {
            throw new ResourceNotFoundException("Customer", "Email", email);
        }
    }

    public void validateCustomerAccountRequestDto(CustomerAccountRequestDto dto) {
        if (Objects.isNull(dto)) {
            throw new InvalidInputException("Resource is Invalid. Required mobile number or emailId information");
        }

        if (ApplicationUtil.isStringNullOrEmpty(dto.getMobileNumber()) && ApplicationUtil.isStringNullOrEmpty(dto.getEmail())) {
            throw new InvalidInputException("Resource is Invalid. Required mobile number or emailId information");
        }
    }
}
