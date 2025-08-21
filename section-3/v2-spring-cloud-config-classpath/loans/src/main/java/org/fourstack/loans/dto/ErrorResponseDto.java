package org.fourstack.loans.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "Error Response", description = "Schema to hold the error response information")
public class ErrorResponseDto {
    @Schema(description = "Status code of the API Response", examples = {"400", "404", "500", "503"})
    private int statusCode;

    @Schema(description = "HTTP status of the API Response", examples = {"BAD_REQUEST", "NOT_FOUND", "INTERNAL_SERVER_ERROR"})
    private HttpStatus status;

    @Schema(description = "Error message of the API Response", examples = {"Record not found for the given input",
            "Data update failed due to insufficient information"})
    private String errorMessage;

    @Schema(description = "API path or URI of the target resource", example = "/cards-service/api/v1/cards/create")
    private String apiPath;

    @Schema(description = "Timestamp at error has been occurred", example = "2025-08-04T22:11:32.240092")
    private LocalDateTime timestamp;
}
