package org.fourstack.accounts.repository;

import org.fourstack.accounts.entity.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountsRepository extends JpaRepository<Accounts, Long> {
    /**
     * Repository method to retrieve the Accounts object from the database using the customerId.
     *
     * @param customerId CustomerId associated to Customer object.
     * @return Optional Container with Accounts Entity or an empty Optional container object.
     */
    Optional<Accounts> findByCustomerId(Long customerId);
}
