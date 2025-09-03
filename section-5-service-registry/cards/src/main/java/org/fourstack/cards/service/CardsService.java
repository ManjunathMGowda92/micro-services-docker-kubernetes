package org.fourstack.cards.service;

import org.fourstack.cards.dto.CardCreationDto;
import org.fourstack.cards.dto.CardsDto;
import org.fourstack.cards.dto.ResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CardsService {

    /**
     * Service method to create a new card using mobile number, card type and limit provided.
     *
     * @param dto Card creation details info object.
     * @return ResponseEntity with created card information.
     */
    ResponseEntity<CardsDto> createCard(CardCreationDto dto);

    /**
     * Service method to retrieve the available cards information based on the mobile number.
     *
     * @param mobileNumber Mobile Number of a customer.
     * @return List of Cards details fetched using mobile number.
     */
    ResponseEntity<List<CardsDto>> retrieveCards(String mobileNumber);

    /**
     * Service method to update the existing Card information.
     *
     * @param dto        Cards update details object.
     * @param cardNumber Card Number info which need to be updated.
     * @return Response object including information about update of details.
     */
    ResponseEntity<ResponseDto> updateCardsDetails(CardsDto dto, String cardNumber);

    /**
     * Service method to delete the cards associated to mobile number provided.
     *
     * @param mobileNumber Mobile number of a customer.
     * @return Response object including information about deleted details.
     */
    ResponseEntity<ResponseDto> deleteCardDetails(String mobileNumber);
}
