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
    private int statusCode;
    private HttpStatus status;
    private String errorMessage;
    private String apiPath;
    private LocalDateTime timestamp;
}
