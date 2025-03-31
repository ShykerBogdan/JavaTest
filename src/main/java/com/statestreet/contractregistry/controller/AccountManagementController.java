package com.statestreet.contractregistry.controller;

import com.statestreet.contractregistry.dto.UserAccountRequest;
import com.statestreet.contractregistry.dto.UserAccountResponse;
import com.statestreet.contractregistry.service.AccountManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * REST controller for user account management operations.
 * Handles API endpoints related to managing user accounts.
 */
@RestController
@RequestMapping("/api/accounts")
public class AccountManagementController {

    private static final Logger log = LoggerFactory.getLogger(AccountManagementController.class);

    private final AccountManagementService accountService;

    /**
     * Constructor for dependency injection
     */
    public AccountManagementController(AccountManagementService accountService) {
        this.accountService = accountService;
    }

    /**
     * Endpoint to create a new user account
     * 
     * @param request The user account details to create
     * @return Response with the created user account details
     */
    @PostMapping
    public ResponseEntity<UserAccountResponse> createUserAccount(@Valid @RequestBody UserAccountRequest request) {
        log.info("Received request to create user account for: {}", request.getUsername());
        UserAccountResponse response = accountService.createUserAccount(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Endpoint to update an existing user account
     * 
     * @param userId The ID of the user account to update
     * @param request The updated user account details
     * @return Response with the updated user account details
     */
    @PutMapping("/{userId}")
    public ResponseEntity<UserAccountResponse> updateUserAccount(
            @PathVariable String userId, 
            @Valid @RequestBody UserAccountRequest request) {
        log.info("Received request to update user account with ID: {}", userId);
        UserAccountResponse response = accountService.updateUserAccount(userId, request);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint to get a user account by ID
     * 
     * @param userId The ID of the user account to retrieve
     * @return Response with the user account details
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserAccountResponse> getUserAccount(@PathVariable String userId) {
        log.info("Received request to get user account with ID: {}", userId);
        UserAccountResponse response = accountService.getUserAccount(userId);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint to delete a user account
     * 
     * @param userId The ID of the user account to delete
     * @return Response indicating success or failure
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<UserAccountResponse> deleteUserAccount(@PathVariable String userId) {
        log.info("Received request to delete user account with ID: {}", userId);
        UserAccountResponse response = accountService.deleteUserAccount(userId);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint to get all user accounts
     * 
     * @return List of all user accounts
     */
    @GetMapping
    public ResponseEntity<List<UserAccountResponse>> getAllUserAccounts() {
        log.info("Received request to get all user accounts");
        List<UserAccountResponse> accounts = accountService.getAllUserAccounts();
        return ResponseEntity.ok(accounts);
    }

    /**
     * Endpoint to get all user accounts in a specific department
     * 
     * @param department The department to filter by
     * @return List of user accounts in the given department
     */
    @GetMapping("/department/{department}")
    public ResponseEntity<List<UserAccountResponse>> getUserAccountsByDepartment(@PathVariable String department) {
        log.info("Received request to get user accounts for department: {}", department);
        List<UserAccountResponse> accounts = accountService.getUserAccountsByDepartment(department);
        return ResponseEntity.ok(accounts);
    }

    /**
     * Endpoint to get all user accounts with a specific role
     * 
     * @param role The role to filter by
     * @return List of user accounts with the given role
     */
    @GetMapping("/role/{role}")
    public ResponseEntity<List<UserAccountResponse>> getUserAccountsByRole(@PathVariable String role) {
        log.info("Received request to get user accounts with role: {}", role);
        List<UserAccountResponse> accounts = accountService.getUserAccountsByRole(role);
        return ResponseEntity.ok(accounts);
    }

    /**
     * Endpoint to assign a wallet address to a user account
     * 
     * @param userId The ID of the user account
     * @param walletAddress The blockchain wallet address to assign
     * @return Response with the updated user account details
     */
    @PostMapping("/{userId}/wallet")
    public ResponseEntity<UserAccountResponse> assignWalletAddress(
            @PathVariable String userId,
            @RequestParam String walletAddress) {
        log.info("Received request to assign wallet address to user account: {}", userId);
        UserAccountResponse response = accountService.assignWalletAddress(userId, walletAddress);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint to generate a new API key for a user account
     * 
     * @param userId The ID of the user account
     * @return Response with the new API key
     */
    @PostMapping("/{userId}/apikey")
    public ResponseEntity<UserAccountResponse> generateApiKey(@PathVariable String userId) {
        log.info("Received request to generate new API key for user account: {}", userId);
        UserAccountResponse response = accountService.generateApiKey(userId);
        return ResponseEntity.ok(response);
    }
}
