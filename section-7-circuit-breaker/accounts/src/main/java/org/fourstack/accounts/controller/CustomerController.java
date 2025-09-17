package org.fourstack.accounts.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.fourstack.accounts.dto.CompleteCustomerDetailsDto;
import org.fourstack.accounts.dto.CustomerAccountRequestDto;
import org.fourstack.accounts.dto.CustomerDetailsDto;
import org.fourstack.accounts.dto.CustomerDto;
import org.fourstack.accounts.dto.ErrorResponseDto;
import org.fourstack.accounts.service.CustomerService;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor(onConstructor_ = @Lazy)
@Validated
@Tag(
        name = "REST-API for Customer Information",
        description = "CRUD APIs to support FETCH Customer details details."
)
public class CustomerController {

    private final CustomerService customerService;

    @Operation(
            summary = "API to fetch complete customer information (customer, accounts, loans and cards info) based on mobile number.",
            description = "REST API to fetch complete customer information (customer, accounts, loans and cards info) based on mobile number.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved the customer details"),
                    @ApiResponse(responseCode = "404", description = "No Customer details found for given mobile number",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
            }
    )
    @GetMapping("/details/{mobileNumber}")
    public ResponseEntity<CompleteCustomerDetailsDto> retrieveCompleteDetailsByMobileNumber(
            @PathVariable @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
            String mobileNumber) {
        return customerService.retrieveCompleteCustomerInfo(mobileNumber);
    }


    @Operation(
            summary = "API to fetch customer information based on mobile number.",
            description = "REST API to fetch customer information based on mobile number.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved the customer details"),
                    @ApiResponse(responseCode = "404", description = "No Customer details found for given mobile number",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
            }
    )
    @GetMapping("/fetch-by-mobile/{mobileNumber}")
    public ResponseEntity<CustomerDto> retrieveDetailsByMobileNumber(
            @PathVariable @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
            String mobileNumber) {
        return customerService.retrieveCustomerByMobileNumber(mobileNumber);
    }

    @Operation(
            summary = "API to fetch customer information based on Email ID.",
            description = "REST API to fetch customer information based on Email ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved the customer details"),
                    @ApiResponse(responseCode = "404", description = "No Customer details found for given Email ID",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
            }
    )
    @GetMapping("/fetch-by-email/{email}")
    public ResponseEntity<CustomerDto> retrieveDetailsByEmail(
            @PathVariable @Email(message = "Invalid email value received") String email) {
        return customerService.retrieveCustomerByEmail(email);
    }

    @Operation(
            summary = "API to fetch complete customer information along with account details based on mobile number or Email ID.",
            description = "REST API fetch complete customer information along with account details based on mobile number or Email ID. " +
                    "User can use either mobile number or Email ID to fetch the details. If both are provided, then priority " +
                    "will be provided for mobile number.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved the customer details"),
                    @ApiResponse(responseCode = "404", description = "No Customer details found for given Email ID or mobile number",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
            }
    )
    @PostMapping("/retrieve-customer")
    public ResponseEntity<CustomerDetailsDto> retrieveCompleteCustomerDetailsByMobile(
            @RequestBody @Valid CustomerAccountRequestDto dto) {
        return customerService.retrieveCompleteCustomerDetails(dto);
    }

}
