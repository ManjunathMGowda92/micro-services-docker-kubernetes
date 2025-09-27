package org.fourstack.accounts.helper.fallback;

import org.fourstack.accounts.dto.CardsDto;
import org.fourstack.accounts.helper.CardsFeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CardsFeignClientFallback implements CardsFeignClient {
    @Override
    public ResponseEntity<List<CardsDto>> retrieveCards(String mobileNumber) {
        /*
         * not providing any dummy response to make sure that complete account information
         * is not having any loans info
         */
        return null;
    }
}
