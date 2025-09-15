package org.fourstack.accounts.helper;

import org.fourstack.accounts.dto.LoansDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("loans")
public interface LoansFeignClient {

    @GetMapping("/loans-service/api/v1/loans/by-mobile-number/{mobileNumber}")
    ResponseEntity<List<LoansDto>> retrieveLoan(@PathVariable String mobileNumber);
}
