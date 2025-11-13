package org.fourstack.notification_service.dto;


import java.time.LocalDateTime;

public record AccountsNotificationUpdateDto(long accountNumber,
                                            String message,
                                            LocalDateTime timestamp) {
}
