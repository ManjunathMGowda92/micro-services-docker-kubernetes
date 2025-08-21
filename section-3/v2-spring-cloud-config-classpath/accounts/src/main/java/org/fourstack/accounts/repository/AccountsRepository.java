package org.fourstack.accounts.repository;

import jakarta.transaction.Transactional;
import org.fourstack.accounts.entity.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

    /**
     * Repository method to remove the Accounts entity from the database using the customerId.
     * Method is annotated with @Transactional and @Modifying annotations, to indicate JPA as the method
     * is modifying the state of the table. So to take extra care with Transaction, @Transactional annotation
     * is used.
     *
     * @param customerId CustomerId associated to Customer object.
     */
    @Transactional
    @Modifying
    void deleteByCustomerId(Long customerId);
}
