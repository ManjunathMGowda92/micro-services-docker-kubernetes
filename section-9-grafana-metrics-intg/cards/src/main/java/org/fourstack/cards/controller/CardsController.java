package org.fourstack.cards.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.fourstack.cards.dto.CardCreationDto;
import org.fourstack.cards.dto.CardsDto;
import org.fourstack.cards.dto.ErrorResponseDto;
import org.fourstack.cards.dto.ResponseDto;
import org.fourstack.cards.service.CardsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cards")
@RequiredArgsConstructor(onConstructor_ = @Lazy)
@Validated
@Tag(
        name = "REST-API for Cards Information",
        description = "CRUD APIs to support CREATE, RETRIEVE, UPDATE and DELETE Operations for Cards details"
)
public class CardsController {
    private static final Logger logger = LoggerFactory.getLogger(CardsController.class);
    private final CardsService cardsService;

    @Operation(
            summary = "API for creating card details",
            description = "REST API for creating the card details",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Card created successfully"),
                    @ApiResponse(responseCode = "400", description = "Card details already exist for given mobile number",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
            }
    )
    @PostMapping
    public ResponseEntity<CardsDto> createCard(@RequestBody @Valid CardCreationDto dto) {
        logger.info("Creating the new card details.");
        return cardsService.createCard(dto);
    }

    @Operation(
            summary = "API to fetch cards information based on mobile number.",
            description = "REST API to fetch cards information based on mobile number.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved the card details associated to mobile number"),
                    @ApiResponse(responseCode = "404", description = "No Card details found for given mobile number",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
            }
    )
    @GetMapping("/{mobileNumber}")
    public ResponseEntity<List<CardsDto>> retrieveCards(
            @PathVariable @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits") String mobileNumber) {
        logger.info("Retrieving the cards details for mobile number : {}", mobileNumber);
        return cardsService.retrieveCards(mobileNumber);
    }

    @Operation(
            summary = "API to update the cards information based on card number.",
            description = "REST API to update the cards information based on card number.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully updated the card details"),
                    @ApiResponse(responseCode = "404", description = "No card details found for given account number",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
            }

    )
    @PutMapping("/{cardNumber}")
    public ResponseEntity<ResponseDto> updateCardsDetails(@RequestBody @Valid CardsDto dto, String cardNumber) {
        return cardsService.updateCardsDetails(dto, cardNumber);
    }

    @Operation(
            summary = "API to delete the cards information based on mobile number.",
            description = "REST API to delete the cards based on mobile number.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully deleted the cards details"),
                    @ApiResponse(responseCode = "404", description = "No card details found for given mobile number",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
            }
    )
    @DeleteMapping("/{mobileNumber}")
    public ResponseEntity<ResponseDto> deleteCard(
            @PathVariable @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
            String mobileNumber) {
        return cardsService.deleteCardDetails(mobileNumber);
    }
}
