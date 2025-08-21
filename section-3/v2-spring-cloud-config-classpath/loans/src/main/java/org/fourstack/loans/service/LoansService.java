package org.fourstack.loans.service;

import org.fourstack.loans.dto.LoanCreateRequestDto;
import org.fourstack.loans.dto.LoansDto;
import org.fourstack.loans.dto.ResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface LoansService {

    /**
     * Service method to create the new loan using the information provided.
     *
     * @param dto New Loan Request information
     * @return Created Loan details information.
     */
    ResponseEntity<LoansDto> createLoan(LoanCreateRequestDto dto);

    /**
     * Service method to retrieve the Loans information using the mobile number
     *
     * @param mobileNumber Mobile number associated to loans
     * @return Retrieved list of Loans information.
     */
    ResponseEntity<List<LoansDto>> retrieveLoan(String mobileNumber);

    /**
     * Service method to retrieve the Loans information using the loan number
     *
     * @param loanNumber Loan number associated to loans
     * @return Retrieved Loans information.
     */
    ResponseEntity<LoansDto> retrieveLoanByLoanNumber(String loanNumber);

    /**
     * Service method to update the loan information
     *
     * @param dto        Loan information details object.
     * @param loanNumber Unique Loan Number associated to loan which need to be updated.
     * @return Response information with success or failure.
     */
    ResponseEntity<ResponseDto> updateLoan(LoansDto dto, String loanNumber);

    /**
     * Service method to delete the loan information using the unique loan number.
     *
     * @param loanNumber Unique loan number associated to a loan.
     * @return Response information with success or failure.
     */
    ResponseEntity<ResponseDto> deleteLoan(String loanNumber);

    /**
     * Service method to delete the loans associated to mobile number.
     *
     * @param mobileNumber Mobile number value associated to a loans.
     * @return Response information with success or failure.
     */
    ResponseEntity<ResponseDto> deleteLoans(String mobileNumber);
}
