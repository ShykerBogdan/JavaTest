package com.statestreet.contractregistry.repository;

import com.statestreet.contractregistry.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing user account data.
 * Handles storage and retrieval of user account information.
 */
@Repository
public interface AccountManagementRepository extends JpaRepository<UserAccount, Long> {
    
    /**
     * Find a user account by its unique user ID
     * 
     * @param userId The unique identifier for the user
     * @return Optional of the user account if found
     */
    Optional<UserAccount> findByUserId(String userId);
    
    /**
     * Find a user account by username
     * 
     * @param username The username to search for
     * @return Optional of the user account if found
     */
    Optional<UserAccount> findByUsername(String username);
    
    /**
     * Find a user account by email
     * 
     * @param email The email address to search for
     * @return Optional of the user account if found
     */
    Optional<UserAccount> findByEmail(String email);
    
    /**
     * Find all user accounts in a specific department
     * 
     * @param department The department to filter by
     * @return List of user accounts in the given department
     */
    List<UserAccount> findByDepartment(String department);
    
    /**
     * Find all user accounts with a specific role
     * 
     * @param role The role to filter by
     * @return List of user accounts with the given role
     */
    List<UserAccount> findByRole(String role);
    
    /**
     * Find all active user accounts
     * 
     * @return List of active user accounts
     */
    List<UserAccount> findByActiveTrue();
    
    /**
     * Find a user account by wallet address
     * 
     * @param walletAddress The blockchain wallet address to search for
     * @return Optional of the user account if found
     */
    Optional<UserAccount> findByWalletAddress(String walletAddress);
    
    /**
     * Check if a user exists with the given user ID
     * 
     * @param userId The user ID to check
     * @return True if exists, false otherwise
     */
    boolean existsByUserId(String userId);
    
    /**
     * Check if a user exists with the given username
     * 
     * @param username The username to check
     * @return True if exists, false otherwise
     */
    boolean existsByUsername(String username);
    
    /**
     * Check if a user exists with the given email
     * 
     * @param email The email to check
     * @return True if exists, false otherwise
     */
    boolean existsByEmail(String email);
}
