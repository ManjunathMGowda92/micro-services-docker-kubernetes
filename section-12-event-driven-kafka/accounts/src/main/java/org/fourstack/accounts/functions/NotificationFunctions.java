package org.fourstack.accounts.functions;

import org.fourstack.accounts.dto.AccountsNotificationUpdateDto;
import org.fourstack.accounts.service.AccountsNotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class NotificationFunctions {
    private static final Logger logger = LoggerFactory.getLogger(NotificationFunctions.class);

    @Bean
    public Consumer<AccountsNotificationUpdateDto> accountNotificationUpdate(AccountsNotificationService notificationService) {
        return accountUpdateDto -> {
            logger.info("Updating the Customer Notification for the account creation with account number : {}",
                    accountUpdateDto.accountNumber());

            boolean isUpdated = notificationService.updateAccountNotification(accountUpdateDto);
            logger.info("Account Notification Record updated ?: {}", isUpdated);
        };
    }
}
