package org.fourstack.accounts.helper;

import org.fourstack.accounts.dto.CardsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "cards")
public interface CardsFeignClient {

    @GetMapping(value = "/cards-service/api/v1/cards/{mobileNumber}", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<CardsDto>> retrieveCards(@PathVariable String mobileNumber);
}
