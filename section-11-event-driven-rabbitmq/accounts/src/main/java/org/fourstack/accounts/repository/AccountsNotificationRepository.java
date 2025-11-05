package org.fourstack.accounts.repository;

import org.fourstack.accounts.entity.AccountsNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountsNotificationRepository extends JpaRepository<AccountsNotification, Long> {
}
