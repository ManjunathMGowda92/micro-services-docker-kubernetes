package org.fourstack.notification_service.functions;

import org.fourstack.notification_service.dto.AccountsMsgDto;
import org.fourstack.notification_service.dto.AccountsNotificationUpdateDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.function.Function;

/**
 * Spring Cloud Functions class to define the notification functions to act as endpoints.
 */
@Configuration
public class NotificationFunctions {
    private static final Logger logger = LoggerFactory.getLogger(NotificationFunctions.class);

    /*
    Method to define the email notification logic for Spring Cloud Function.
     */
    @Bean(name = "email")
    public Function<AccountsMsgDto, AccountsMsgDto> emailNotification() {
        return accountsMsgDto -> {
            logger.info("Sending email notification with details : {}", accountsMsgDto.toString());
            return accountsMsgDto;
        };
    }

    /*
    Method to define the SMS notification logic for the Spring Cloud Function.
     */
    @Bean(name = "sms")
    public Function<AccountsMsgDto, AccountsNotificationUpdateDto> smsNotification() {
        return accountsMsgDto -> {
            logger.info("Sending sms notification with details : {}", accountsMsgDto.toString());
            return new AccountsNotificationUpdateDto(accountsMsgDto.accountNumber(),
                    "Successfully sent account creation notification to customer", LocalDateTime.now());
        };
    }
}
