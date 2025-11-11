package org.fourstack.accounts.service;

import lombok.RequiredArgsConstructor;
import org.fourstack.accounts.dto.AccountCreationNotificationDto;
import org.fourstack.accounts.dto.AccountsNotificationUpdateDto;
import org.fourstack.accounts.entity.Accounts;
import org.fourstack.accounts.entity.AccountsNotification;
import org.fourstack.accounts.entity.Customer;
import org.fourstack.accounts.exception.ResourceNotFoundException;
import org.fourstack.accounts.repository.AccountsNotificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AccountsNotificationServiceImpl implements AccountsNotificationService {
    private static final Logger logger = LoggerFactory.getLogger(AccountsNotificationServiceImpl.class);

    private final StreamBridge streamBridge;
    private final AccountsNotificationRepository repository;

    @Override
    public void notifyAccountCreation(Accounts accounts, Customer customer) {
        AccountCreationNotificationDto notificationDto = new AccountCreationNotificationDto(accounts.getAccountNumber(),
                customer.getName(), customer.getEmail(), customer.getMobileNumber());
        logger.info("Notifying the customer with account creation details - {}", notificationDto);
        createAccountNotification(notificationDto.accountNumber(), customer.getCustomerId());
        var result = streamBridge.send("account-notification-out-0", notificationDto);
        logger.info("Is customer notification pushed to queue successfully ? : {}", result);

    }

    @Override
    public void createAccountNotification(Long accountNumber, Long customerId) {
        AccountsNotification notification = new AccountsNotification();
        notification.setAccountNumber(accountNumber);
        notification.setCustomerId(customerId);
        notification.setCustomerNotified(false);
        notification.setCreatedBy("Anonymous");
        notification.setCreationTimestamp(LocalDateTime.now());

        repository.save(notification);
    }

    @Override
    public boolean updateAccountNotification(AccountsNotificationUpdateDto dto) {
        if (dto != null) {
            AccountsNotification notification = repository.findById(dto.accountNumber())
                    .orElseThrow(() -> new ResourceNotFoundException("Account Notification", "AccountNumber", dto.toString()));
            notification.setCustomerNotified(true);
            notification.setUpdatedBy("System");
            notification.setUpdatedTimestamp(LocalDateTime.now());

            repository.save(notification);
            return true;
        }
        return false;
    }
}
