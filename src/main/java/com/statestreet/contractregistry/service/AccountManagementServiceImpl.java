package com.statestreet.contractregistry.service;

import com.statestreet.contractregistry.dto.UserAccountRequest;
import com.statestreet.contractregistry.dto.UserAccountResponse;
import com.statestreet.contractregistry.entity.UserAccount;
import com.statestreet.contractregistry.exception.ResourceNotFoundException;
import com.statestreet.contractregistry.repository.AccountManagementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementation of the AccountManagementService interface.
 * Handles operations related to user account management.
 */
@Service
public class AccountManagementServiceImpl implements AccountManagementService {

    private static final Logger log = LoggerFactory.getLogger(AccountManagementServiceImpl.class);
    
    private final AccountManagementRepository accountRepository;
    
    /**
     * Constructor for dependency injection
     */
    public AccountManagementServiceImpl(AccountManagementRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional
    public UserAccountResponse createUserAccount(UserAccountRequest request) {
        log.info("Creating new user account for: {}", request.getUsername());
        
        // Check if username or email already exists
        if (accountRepository.existsByUsername(request.getUsername())) {
            log.error("Username already exists: {}", request.getUsername());
            throw new IllegalArgumentException("Username already exists: " + request.getUsername());
        }
        
        if (accountRepository.existsByEmail(request.getEmail())) {
            log.error("Email already exists: {}", request.getEmail());
            throw new IllegalArgumentException("Email already exists: " + request.getEmail());
        }
        
        // Create user account
        String userId = generateUserId();
        UserAccount account = UserAccount.builder()
                .userId(userId)
                .username(request.getUsername())
                .email(request.getEmail())
                .department(request.getDepartment())
                .role(request.getRole())
                .active(true)
                .build();
        
        UserAccount savedAccount = accountRepository.save(account);
        log.info("User account created with ID: {}", userId);
        
        return createUserAccountResponse(savedAccount);
    }

    @Override
    @Transactional
    public UserAccountResponse updateUserAccount(String userId, UserAccountRequest request) {
        log.info("Updating user account with ID: {}", userId);
        
        UserAccount account = findUserAccountById(userId);
        
        // Check if username is being changed and if it already exists
        if (!account.getUsername().equals(request.getUsername()) && 
                accountRepository.existsByUsername(request.getUsername())) {
            log.error("Username already exists: {}", request.getUsername());
            throw new IllegalArgumentException("Username already exists: " + request.getUsername());
        }
        
        // Check if email is being changed and if it already exists
        if (!account.getEmail().equals(request.getEmail()) && 
                accountRepository.existsByEmail(request.getEmail())) {
            log.error("Email already exists: {}", request.getEmail());
            throw new IllegalArgumentException("Email already exists: " + request.getEmail());
        }
        
        // Update account details
        account.setUsername(request.getUsername());
        account.setEmail(request.getEmail());
        account.setDepartment(request.getDepartment());
        account.setRole(request.getRole());
        
        UserAccount updatedAccount = accountRepository.save(account);
        log.info("User account updated: {}", userId);
        
        return createUserAccountResponse(updatedAccount);
    }

    @Override
    public UserAccountResponse getUserAccount(String userId) {
        log.info("Getting user account with ID: {}", userId);
        
        UserAccount account = findUserAccountById(userId);
        return createUserAccountResponse(account);
    }

    @Override
    @Transactional
    public UserAccountResponse deleteUserAccount(String userId) {
        log.info("Deleting user account with ID: {}", userId);
        
        UserAccount account = findUserAccountById(userId);
        
        // Soft delete - mark as inactive
        account.setActive(false);
        UserAccount updatedAccount = accountRepository.save(account);
        log.info("User account deactivated: {}", userId);
        
        return createUserAccountResponse(updatedAccount);
    }

    @Override
    public List<UserAccountResponse> getAllUserAccounts() {
        log.info("Getting all user accounts");
        
        List<UserAccount> accounts = accountRepository.findAll();
        return accounts.stream()
                .map(this::createUserAccountResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserAccountResponse> getUserAccountsByDepartment(String department) {
        log.info("Getting user accounts for department: {}", department);
        
        List<UserAccount> accounts = accountRepository.findByDepartment(department);
        return accounts.stream()
                .map(this::createUserAccountResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserAccountResponse> getUserAccountsByRole(String role) {
        log.info("Getting user accounts with role: {}", role);
        
        List<UserAccount> accounts = accountRepository.findByRole(role);
        return accounts.stream()
                .map(this::createUserAccountResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserAccountResponse assignWalletAddress(String userId, String walletAddress) {
        log.info("Assigning wallet address to user account: {}", userId);
        
        UserAccount account = findUserAccountById(userId);
        account.setWalletAddress(walletAddress);
        
        UserAccount updatedAccount = accountRepository.save(account);
        log.info("Wallet address assigned to user: {}", userId);
        
        return createUserAccountResponse(updatedAccount);
    }

    @Override
    @Transactional
    public UserAccountResponse generateApiKey(String userId) {
        log.info("Generating new API key for user account: {}", userId);
        
        UserAccount account = findUserAccountById(userId);
        
        String apiKey = UUID.randomUUID().toString();
        account.setApiKey(apiKey);
        
        UserAccount updatedAccount = accountRepository.save(account);
        log.info("API key generated for user: {}", userId);
        
        return createUserAccountResponse(updatedAccount);
    }
    
    /**
     * Helper method to find a user account by ID
     */
    private UserAccount findUserAccountById(String userId) {
        return accountRepository.findByUserId(userId)
                .orElseThrow(() -> {
                    log.error("User account not found with ID: {}", userId);
                    return new ResourceNotFoundException("User account not found with ID: " + userId);
                });
    }
    
    /**
     * Helper method to create a user account response from entity
     */
    private UserAccountResponse createUserAccountResponse(UserAccount account) {
        return UserAccountResponse.builder()
                .userId(account.getUserId())
                .username(account.getUsername())
                .email(account.getEmail())
                .department(account.getDepartment())
                .role(account.getRole())
                .walletAddress(account.getWalletAddress())
                .active(account.isActive())
                .createdAt(account.getCreatedAt())
                .lastLogin(account.getLastLogin())
                .build();
    }
    
    /**
     * Helper method to generate a unique user ID
     */
    private String generateUserId() {
        return "USER-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
