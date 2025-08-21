package org.fourstack.accounts.repository;

import org.fourstack.accounts.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    /**
     * Method to check the Customer object using mobile number.
     *
     * @param mobileNumber Input Mobile Number
     * @return boolean value indicating that Customer exists with mobile number or not
     */
    boolean existsByMobileNumber(String mobileNumber);

    /**
     * Method to retrieve the Customer object using mobile number.
     *
     * @param mobileNumber Input Mobile Number
     * @return Optional container with Customer object if exist or an empty Optional container.
     */
    Optional<Customer> findByMobileNumber(String mobileNumber);

    /**
     * Method to check the Customer object using emailId value.
     *
     * @param email Input emailId provided.
     * @return boolean value indicating that Customer exists with emailId or not
     */
    boolean existsByEmail(String email);

    /**
     * Method to retrieve the Customer object using emailId value.
     *
     * @param email Input emailId provided.
     * @return Optional container with Customer object if exist or an empty Optional container.
     */
    Optional<Customer> findByEmail(String email);
}
