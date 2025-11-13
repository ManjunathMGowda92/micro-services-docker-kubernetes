package org.fourstack.accounts.dto;

import java.time.LocalDateTime;

public record AccountsNotificationUpdateDto(long accountNumber,
                                            String message,
                                            LocalDateTime timestamp) {
}
