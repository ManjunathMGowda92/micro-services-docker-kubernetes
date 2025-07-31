package org.fourstack.accounts.validation;

import lombok.RequiredArgsConstructor;
import org.fourstack.accounts.dto.CustomerDto;
import org.fourstack.accounts.exception.CustomerAlreadyExistException;
import org.fourstack.accounts.repository.CustomerRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

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
}
