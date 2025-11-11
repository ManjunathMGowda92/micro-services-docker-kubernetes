package org.fourstack.accounts.helper.fallback;

import org.fourstack.accounts.dto.LoansDto;
import org.fourstack.accounts.helper.LoansFeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LoansFeignClientFallback implements LoansFeignClient {
    @Override
    public ResponseEntity<List<LoansDto>> retrieveLoan(String mobileNumber) {
        /*
         * not providing any dummy response to make sure that complete account information
         * is not having any loans info
         */
        return null;
    }
}
