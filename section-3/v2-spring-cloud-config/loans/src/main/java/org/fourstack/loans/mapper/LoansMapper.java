package org.fourstack.loans.mapper;

import org.fourstack.loans.dto.LoanCreateRequestDto;
import org.fourstack.loans.dto.LoansDto;
import org.fourstack.loans.entity.Loans;
import org.fourstack.loans.util.ApplicationUtil;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class LoansMapper {
    public LoansDto mapToLoansDto(Loans loans) {
        LoansDto loansDto = new LoansDto();
        loansDto.setLoanNumber(loans.getLoanNumber());
        loansDto.setLoanType(loans.getLoanType());
        loansDto.setMobileNumber(loans.getMobileNumber());
        loansDto.setTotalLoan(loans.getTotalLoanAmount());
        loansDto.setAmountPaid(loans.getAmountPaid());
        loansDto.setOutstandingAmount(loans.getOutstandingAmount());
        return loansDto;
    }

    public void mapToLoans(Loans loans, LoansDto loansDto) {
        loans.setLoanType(loansDto.getLoanType());
        loans.setMobileNumber(loansDto.getMobileNumber());
        loans.setTotalLoanAmount(loansDto.getTotalLoan());
        loans.setAmountPaid(loansDto.getAmountPaid());
        loans.setOutstandingAmount(loansDto.getOutstandingAmount());
        loans.setUpdatedBy("Anonymous");
        loans.setUpdatedTimestamp(LocalDateTime.now());
    }

    public Loans mapToNewLoan(LoanCreateRequestDto dto) {
        Loans loans = new Loans();
        loans.setLoanNumber(String.valueOf(ApplicationUtil.generateLoanNumber()));
        loans.setLoanType(dto.getLoanType());
        loans.setMobileNumber(dto.getMobileNumber());
        loans.setTotalLoanAmount(dto.getTotalLoanAmount());
        loans.setAmountPaid(0);
        loans.setOutstandingAmount(loans.getTotalLoanAmount());
        loans.setCreatedBy("Anonymous");
        loans.setCreationTimestamp(LocalDateTime.now());
        return loans;
    }
}
