package org.fourstack.loans.service;

import lombok.RequiredArgsConstructor;
import org.fourstack.loans.dto.LoanCreateRequestDto;
import org.fourstack.loans.dto.LoansDto;
import org.fourstack.loans.dto.ResponseDto;
import org.fourstack.loans.entity.Loans;
import org.fourstack.loans.exception.LoanAlreadyExistsException;
import org.fourstack.loans.exception.ResourceNotFoundException;
import org.fourstack.loans.mapper.LoansMapper;
import org.fourstack.loans.repository.LoansRepository;
import org.fourstack.loans.util.ApplicationUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoansServiceImpl implements LoansService {
    private final LoansRepository repository;
    private final LoansMapper mapper;

    /**
     * Service method to create the new loan using the information provided.
     *
     * @param dto New Loan Request information
     * @return Created Loan details information.
     */
    @Override
    public ResponseEntity<LoansDto> createLoan(LoanCreateRequestDto dto) {
        List<Loans> loans = repository.findByMobileNumber(dto.getMobileNumber());
        if (ApplicationUtil.isCollectionNotNullOrEmpty(loans)) {
            throw new LoanAlreadyExistsException("Loan already exist for the mobile number : " + dto.getMobileNumber());
        }
        Loans loanObject = mapper.mapToNewLoan(dto);
        repository.save(loanObject);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.mapToLoansDto(loanObject));
    }

    /**
     * Service method to retrieve the Loans information using the mobile number
     *
     * @param mobileNumber Mobile number associated to loans
     * @return Retrieved list of Loans information.
     */
    @Override
    public ResponseEntity<List<LoansDto>> retrieveLoan(String mobileNumber) {
        List<Loans> loans = repository.findByMobileNumber(mobileNumber);
        if (ApplicationUtil.isCollectionNotNullOrEmpty(loans)) {
            return ResponseEntity.ok(loans.stream()
                    .map(mapper::mapToLoansDto)
                    .toList());
        }
        throw new ResourceNotFoundException("Loan", "Mobile number", mobileNumber);
    }

    /**
     * Service method to retrieve the Loans information using the loan number
     *
     * @param loanNumber Loan number associated to loans
     * @return Retrieved Loans information.
     */
    @Override
    public ResponseEntity<LoansDto> retrieveLoanByLoanNumber(String loanNumber) {
        Optional<Loans> optionalLoan = repository.findByLoanNumber(loanNumber);
        Loans loans = optionalLoan.orElseThrow(() -> new ResourceNotFoundException("Loan", "Loan number", loanNumber));
        return ResponseEntity.ok(mapper.mapToLoansDto(loans));
    }

    /**
     * Service method to update the loan information
     *
     * @param dto        Loan information details object.
     * @param loanNumber Unique Loan Number associated to loan which need to be updated.
     * @return Response information with success or failure.
     */
    @Override
    public ResponseEntity<ResponseDto> updateLoan(LoansDto dto, String loanNumber) {
        Optional<Loans> optionalLoan = repository.findByLoanNumber(loanNumber);
        Loans loans = optionalLoan.orElseThrow(() -> new ResourceNotFoundException("Loan", "Loan number", loanNumber));
        mapper.mapToLoans(loans, dto);
        repository.save(loans);
        return ResponseEntity.ok(ResponseDto.builder()
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .statusMsg("Loan updated successfully")
                .build());
    }

    /**
     * Service method to delete the loan information using the unique loan number.
     *
     * @param loanNumber Unique loan number associated to a loan.
     * @return Response information with success or failure.
     */
    @Override
    public ResponseEntity<ResponseDto> deleteLoan(String loanNumber) {
        Optional<Loans> optionalLoan = repository.findByLoanNumber(loanNumber);
        Loans loans = optionalLoan.orElseThrow(() -> new ResourceNotFoundException("Loan", "Loan number", loanNumber));
        repository.delete(loans);
        return ResponseEntity.ok(ResponseDto.builder()
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .statusMsg("Loan deleted successfully")
                .build());
    }

    /**
     * Service method to delete the loans associated to mobile number.
     *
     * @param mobileNumber Mobile number value associated to a loans.
     * @return Response information with success or failure.
     */
    @Override
    public ResponseEntity<ResponseDto> deleteLoans(String mobileNumber) {
        List<Loans> loans = repository.findByMobileNumber(mobileNumber);
        if (ApplicationUtil.isCollectionNotNullOrEmpty(loans)) {
            repository.deleteAll(loans);
            return ResponseEntity.ok(ResponseDto.builder()
                    .statusCode(HttpStatus.OK.value())
                    .status(HttpStatus.OK)
                    .statusMsg("Loans deleted successfully which are associated to mobile number : " + mobileNumber)
                    .build());
        }
        throw new ResourceNotFoundException("Loans", "Mobile Number", mobileNumber);
    }
}
