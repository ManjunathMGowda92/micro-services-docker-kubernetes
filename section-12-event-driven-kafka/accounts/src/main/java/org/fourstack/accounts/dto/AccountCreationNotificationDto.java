package org.fourstack.accounts.dto;

public record AccountCreationNotificationDto(long accountNumber, String name,
                                             String email, String mobileNumber) {
}
