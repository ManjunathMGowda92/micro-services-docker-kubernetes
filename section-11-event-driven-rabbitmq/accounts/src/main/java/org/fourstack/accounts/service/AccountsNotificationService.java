package org.fourstack.accounts.service;

import org.fourstack.accounts.dto.AccountsNotificationUpdateDto;
import org.fourstack.accounts.entity.Accounts;
import org.fourstack.accounts.entity.Customer;

public interface AccountsNotificationService {

    /**
     * Method responsible to push the account creation message to customer notification.
     */
    void notifyAccountCreation(Accounts accounts, Customer customer);

    /**
     * Method responsible to create the Account Notification object in Database.
     *
     * @param accountNumber Account Number Created.
     * @param customerId    CustomerId for which account is created.
     */
    void createAccountNotification(Long accountNumber, Long customerId);

    /**
     * Method responsible to update the Account Notification with customer notified value.
     * If the customer is successfully notified, then the customerNotified flag will be set to ON.
     *
     * @param dto Details of  Customer notification update including the message.
     * @return Boolean value to indicate the data object got updated or not.
     */
    boolean updateAccountNotification(AccountsNotificationUpdateDto dto);

}
