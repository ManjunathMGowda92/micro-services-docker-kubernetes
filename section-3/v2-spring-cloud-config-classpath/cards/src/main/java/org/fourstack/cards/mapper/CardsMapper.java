package org.fourstack.cards.mapper;

import org.fourstack.cards.dto.CardCreationDto;
import org.fourstack.cards.dto.CardsDto;
import org.fourstack.cards.entity.Cards;
import org.fourstack.cards.util.ApplicationUtil;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class CardsMapper {

    public Cards createNewCard(CardCreationDto dto) {
        Cards cards = new Cards();
        cards.setCardNumber(String.valueOf(ApplicationUtil.generateCardNumber()));
        cards.setMobileNumber(dto.getMobileNumber());
        cards.setCardType(dto.getCardType());
        cards.setTotalLimit(dto.getTotalLimit());
        cards.setAmountUsed(0);
        cards.setAvailableAmount(dto.getTotalLimit());
        cards.setValidTill(LocalDate.now().plusYears(3));
        cards.setCreatedBy("ANONYMOUS");
        cards.setCreationTimestamp(LocalDateTime.now());
        return cards;
    }

    public Cards mapToCards(CardsDto dto) {
        Cards target = new Cards();
        target.setCardNumber(dto.getCardNumber());
        target.setCardType(dto.getCardType());
        target.setMobileNumber(dto.getMobileNumber());
        target.setTotalLimit(dto.getTotalLimit());
        target.setAvailableAmount(dto.getAvailableAmount());
        target.setAmountUsed(dto.getAmountUsed());
        target.setValidTill(LocalDate.now().plusYears(3));

        target.setCreatedBy("ANONYMOUS");
        target.setCreationTimestamp(LocalDateTime.now());
        return target;
    }

    public void mapToCards(CardsDto dto, Cards cards) {
        cards.setMobileNumber(dto.getMobileNumber());
        cards.setTotalLimit(dto.getTotalLimit());
        cards.setAvailableAmount(dto.getAvailableAmount());
        cards.setAmountUsed(dto.getAmountUsed());

        cards.setUpdatedBy("ANONYMOUS");
        cards.setUpdatedTimestamp(LocalDateTime.now());
    }

    public CardsDto mapToCardsDto(Cards cards) {
        CardsDto dto = new CardsDto();
        dto.setCardNumber(cards.getCardNumber());
        dto.setCardType(cards.getCardType());
        dto.setMobileNumber(cards.getMobileNumber());
        dto.setTotalLimit(cards.getTotalLimit());
        dto.setAvailableAmount(cards.getAvailableAmount());
        dto.setAmountUsed(cards.getAmountUsed());

        return dto;
    }
}
