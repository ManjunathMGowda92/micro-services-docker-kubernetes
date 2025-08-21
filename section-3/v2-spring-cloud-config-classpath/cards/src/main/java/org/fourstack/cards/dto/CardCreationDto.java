package org.fourstack.cards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
@Schema(name = "Create Card Info", description = "Details to create a new Card")
public class CardCreationDto {

    @Schema(description = "Mobile Number of Customer", example = "4354437687")
    @NotEmpty(message = "Mobile Number can not be a null or empty")
    @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile Number must be 10 digits")
    private String mobileNumber;

    @Schema(description = "Type of the card", example = "Credit Card")
    @NotEmpty(message = "CardType can not be a null or empty")
    private String cardType;

    @Schema(description = "Total amount limit available against a card", example = "100000")
    @Positive(message = "Total card limit should be greater than zero")
    private int totalLimit;
}
