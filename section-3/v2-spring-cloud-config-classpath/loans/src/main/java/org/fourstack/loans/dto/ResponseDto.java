package org.fourstack.loans.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "Response", description = "Schema to hold the successful response information")
public class ResponseDto {
    @Schema(description = "Status code of the API Response", examples = {"200", "201"})
    private int statusCode;

    @Schema(description = "HTTP status of the API Response", examples = {"OK", "CREATED"})
    private HttpStatus status;

    @Schema(description = "Status message in the response", examples = {"Account Created Successfully",
            "Records updated successfully", "Record deleted successfully"})
    private String statusMsg;
}
