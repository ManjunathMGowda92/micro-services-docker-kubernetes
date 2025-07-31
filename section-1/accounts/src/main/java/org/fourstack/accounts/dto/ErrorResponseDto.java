package org.fourstack.accounts.dto;

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
public class ErrorResponseDto {
    private String apiPath;
    private HttpStatus status;
    private int statusCode;
    private String errorMessage;
    private LocalDateTime timestamp;
}
