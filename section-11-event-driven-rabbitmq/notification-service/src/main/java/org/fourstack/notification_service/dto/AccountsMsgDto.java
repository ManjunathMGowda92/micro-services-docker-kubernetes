package org.fourstack.notification_service.dto;

public record AccountsMsgDto(long accountNumber, String name,
                             String email, String mobileNumber) {
}
