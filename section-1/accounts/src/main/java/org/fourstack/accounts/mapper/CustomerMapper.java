package org.fourstack.accounts.mapper;

import org.fourstack.accounts.dto.CustomerDto;
import org.fourstack.accounts.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public CustomerDto mapToCustomerDto(Customer customer) {
        return CustomerDto.builder()
                .email(customer.getEmail())
                .name(customer.getName())
                .mobileNumber(customer.getMobileNumber())
                .build();
    }

    public Customer mapToCustomer(CustomerDto dto) {
        Customer customer = new Customer();
        customer.setName(dto.getName());
        customer.setEmail(dto.getEmail());
        customer.setMobileNumber(dto.getMobileNumber());
        return customer;
    }

    public void mapToCustomer(CustomerDto dto, Customer customer) {
        customer.setName(dto.getName());
        customer.setEmail(dto.getEmail());
        customer.setMobileNumber(dto.getMobileNumber());
    }
}
