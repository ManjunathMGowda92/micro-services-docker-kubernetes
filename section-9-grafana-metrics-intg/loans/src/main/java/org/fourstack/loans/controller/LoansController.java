package org.fourstack.loans.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.fourstack.loans.dto.ErrorResponseDto;
import org.fourstack.loans.dto.LoanCreateRequestDto;
import org.fourstack.loans.dto.LoansDto;
import org.fourstack.loans.dto.ResponseDto;
import org.fourstack.loans.service.LoansService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/loans")
@RequiredArgsConstructor(onConstructor_ = @Lazy)
@Validated
public class LoansController {
    private static final Logger logger = LoggerFactory.getLogger(LoansController.class);
    private final LoansService service;

    @Operation(
            summary = "API for creating loan details",
            description = "REST API for creating the loan details",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Loan created successfully"),
                    @ApiResponse(responseCode = "400", description = "Loan details already exist for given mobile number",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
            }
    )
    @PostMapping
    public ResponseEntity<LoansDto> createLoan(@RequestBody @Valid LoanCreateRequestDto dto) {
        return service.createLoan(dto);
    }

    @Operation(
            summary = "API to fetch loans information based on mobile number.",
            description = "REST API to fetch loans information based on mobile number.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved the all loan details associated to mobile number"),
                    @ApiResponse(responseCode = "404", description = "No loan details found for given mobile number",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
            }
    )
    @GetMapping("/by-mobile-number/{mobileNumber}")
    public ResponseEntity<List<LoansDto>> retrieveLoan(
            @PathVariable @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
            String mobileNumber) {
        logger.info("Fetching loan details based on mobile number : {}", mobileNumber);
        return service.retrieveLoan(mobileNumber);
    }

    @Operation(
            summary = "API to fetch loan information based on loan number.",
            description = "REST API to fetch loan information based on loan number.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved the loan details by loan number"),
                    @ApiResponse(responseCode = "404", description = "No loan details found for given loan number",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
            }
    )
    @GetMapping("/{loanNumber}")
    public ResponseEntity<LoansDto> retrieveLoanByLoanNumber(@PathVariable String loanNumber) {
        logger.info("Fetching loan details based on loan number : {}", loanNumber);
        return service.retrieveLoanByLoanNumber(loanNumber);
    }

    @Operation(
            summary = "API to update the loan information based on loan number.",
            description = "REST API to update the loan information based on loan number.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully updated the loan details"),
                    @ApiResponse(responseCode = "404", description = "No loan details found for given account number",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
            }

    )
    @PutMapping("/{loanNumber}")
    public ResponseEntity<ResponseDto> updateLoan(@RequestBody @Valid LoansDto dto,
                                                  @PathVariable String loanNumber) {
        return service.updateLoan(dto, loanNumber);
    }


    @Operation(
            summary = "API to delete the loan information based on loan number.",
            description = "REST API to delete the loan based on loan number.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully deleted the loan details"),
                    @ApiResponse(responseCode = "404", description = "No loan details found for given loan number",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
            }
    )
    @DeleteMapping("/{loanNumber}")
    public ResponseEntity<ResponseDto> deleteLoan(String loanNumber) {
        return service.deleteLoan(loanNumber);
    }

    @Operation(
            summary = "API to delete the loans associated to mobile number.",
            description = "REST API to delete the loans associated to mobile number.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully deleted the loans associated to mobile number"),
                    @ApiResponse(responseCode = "404", description = "No loan details found for given mobile number",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
            }
    )
    @DeleteMapping("/remove-by-mobile/{mobileNumber}")
    public ResponseEntity<ResponseDto> deleteLoans(String mobileNumber) {
        return service.deleteLoans(mobileNumber);
    }
}
