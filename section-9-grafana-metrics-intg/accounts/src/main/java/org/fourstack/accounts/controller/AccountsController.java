package org.fourstack.accounts.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.fourstack.accounts.dto.CustomerDetailsDto;
import org.fourstack.accounts.dto.CustomerDto;
import org.fourstack.accounts.dto.ErrorResponseDto;
import org.fourstack.accounts.dto.ResponseDto;
import org.fourstack.accounts.service.AccountsService;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/accounts", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor(onConstructor_ = @Lazy)
@Validated
@Tag(
        name = "REST-API for Accounts Information",
        description = "CRUD APIs to support CREATE, FETCH, UPDATE and DELETE Operations for Account details."
)
public class AccountsController {
    private final AccountsService accountsService;

    @Operation(
            summary = "API for creating account information.",
            description = "REST API for creating the account information with provided details.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Account created successfully for the customer"),
                    @ApiResponse(responseCode = "400", description = "Customer already exist for the given mobile number or Email",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
            }
    )
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@RequestBody @Valid CustomerDto customerDto) {
        return accountsService.createAccount(customerDto);
    }

    @Operation(
            summary = "API to update the customer and accounts information based on account number.",
            description = "REST API to update the customer and accounts information based on account number.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully updated the customer details and account details"),
                    @ApiResponse(responseCode = "404", description = "No Customer details found for given account number",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
            }

    )
    @PutMapping("/{accountNumber}")
    public ResponseEntity<ResponseDto> updateAccountDetails(@RequestBody @Valid CustomerDetailsDto dto,
                                                            @PathVariable long accountNumber) {
        return accountsService.updateAccount(dto, accountNumber);
    }

    @Operation(
            summary = "API to delete the customer and accounts information based on mobile number.",
            description = "REST API to delete the customer and accounts information based on mobile number.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully deleted the customer details and account details"),
                    @ApiResponse(responseCode = "404", description = "No Customer details found for given mobile number",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
            }
    )
    @DeleteMapping("/delete-by-mobile/{mobileNumber}")
    public ResponseEntity<ResponseDto> deleteAccount(
            @PathVariable @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
            String mobileNumber) {
        return accountsService.deleteAccount(mobileNumber);
    }
}
