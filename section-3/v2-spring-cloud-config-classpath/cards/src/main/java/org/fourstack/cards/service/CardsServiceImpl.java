package org.fourstack.cards.service;

import lombok.RequiredArgsConstructor;
import org.fourstack.cards.dto.CardCreationDto;
import org.fourstack.cards.dto.CardsDto;
import org.fourstack.cards.dto.ResponseDto;
import org.fourstack.cards.entity.Cards;
import org.fourstack.cards.exception.CardAlreadyExistsException;
import org.fourstack.cards.exception.ResourceNotFoundException;
import org.fourstack.cards.mapper.CardsMapper;
import org.fourstack.cards.repository.CardsRepository;
import org.fourstack.cards.util.ApplicationUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = @Lazy)
public class CardsServiceImpl implements CardsService {
    private final CardsRepository repository;
    private final CardsMapper mapper;

    /**
     * Service method to create a new card using mobile number, card type and limit provided.
     *
     * @param dto Card creation details info object.
     * @return ResponseEntity with created card information.
     */
    @Override
    public ResponseEntity<CardsDto> createCard(CardCreationDto dto) {
        // Check if card exist with the mobile number. If exist throw an exception
        List<Cards> cards = repository.findByMobileNumber(dto.getMobileNumber());
        if (ApplicationUtil.isCollectionNotNullOrEmpty(cards)) {
            throw new CardAlreadyExistsException("Card already exist with given mobile number : " + dto.getMobileNumber());
        }

        // Save the card.
        Cards savedCard = repository.save(mapper.createNewCard(dto));

        // Return the created card details.
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.mapToCardsDto(savedCard));
    }

    /**
     * Service method to retrieve the available cards information based on the mobile number.
     *
     * @param mobileNumber Mobile Number of a customer.
     * @return List of Cards details fetched using mobile number.
     */
    @Override
    public ResponseEntity<List<CardsDto>> retrieveCards(String mobileNumber) {
        List<Cards> cards = repository.findByMobileNumber(mobileNumber);
        if (ApplicationUtil.isCollectionNotNullOrEmpty(cards)) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(cards.stream()
                            .map(mapper::mapToCardsDto)
                            .toList());
        }
        throw new ResourceNotFoundException("Card", "mobileNumber", mobileNumber);
    }

    /**
     * Service method to update the existing Card information.
     *
     * @param dto        Cards update details object.
     * @param cardNumber Card Number info which need to be updated.
     * @return Response object including information about update of details.
     */
    @Override
    public ResponseEntity<ResponseDto> updateCardsDetails(CardsDto dto, String cardNumber) {
        Cards cards = repository.findByCardNumber(cardNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Card", "Card Number", cardNumber));
        mapper.mapToCards(dto, cards);
        repository.save(cards);
        return ResponseEntity.ok(ResponseDto.builder()
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .statusMsg("Card Info updated successfully")
                .build());
    }


    /**
     * Service method to delete the cards associated to mobile number provided.
     *
     * @param mobileNumber Mobile number of a customer.
     * @return Response object including information about deleted details.
     */
    @Override
    public ResponseEntity<ResponseDto> deleteCardDetails(String mobileNumber) {
        List<Cards> cards = repository.findByMobileNumber(mobileNumber);
        if (ApplicationUtil.isCollectionNotNullOrEmpty(cards)) {
            repository.deleteAll(cards);
            return ResponseEntity.ok(ResponseDto.builder()
                    .status(HttpStatus.OK)
                    .statusCode(HttpStatus.OK.value())
                    .statusMsg("Cards deleted successfully")
                    .build());
        }
        throw new ResourceNotFoundException("Card", "Mobile Number", mobileNumber);
    }
}
