package com.statestreet.contractregistry.service;

import com.statestreet.contractregistry.dto.UserAccountRequest;
import com.statestreet.contractregistry.dto.UserAccountResponse;
import java.util.List;

/**
 * Service interface for user account management operations.
 * Handles logic for managing user accounts (create, update, delete, etc.).
 */
public interface AccountManagementService {

    /**
     * Creates a new user account
     *
     * @param request The user account details to create
     * @return Response with the created user account details
     */
    UserAccountResponse createUserAccount(UserAccountRequest request);

    /**
     * Updates an existing user account
     *
     * @param userId The ID of the user account to update
     * @param request The updated user account details
     * @return Response with the updated user account details
     */
    UserAccountResponse updateUserAccount(String userId, UserAccountRequest request);

    /**
     * Retrieves a user account by its ID
     *
     * @param userId The ID of the user account to retrieve
     * @return Response with the user account details
     */
    UserAccountResponse getUserAccount(String userId);

    /**
     * Deletes a user account
     *
     * @param userId The ID of the user account to delete
     * @return Response indicating success or failure
     */
    UserAccountResponse deleteUserAccount(String userId);

    /**
     * Retrieves all user accounts
     *
     * @return List of all user accounts
     */
    List<UserAccountResponse> getAllUserAccounts();

    /**
     * Retrieves all user accounts in a specific department
     *
     * @param department The department to filter by
     * @return List of user accounts in the given department
     */
    List<UserAccountResponse> getUserAccountsByDepartment(String department);

    /**
     * Retrieves all user accounts with a specific role
     *
     * @param role The role to filter by
     * @return List of user accounts with the given role
     */
    List<UserAccountResponse> getUserAccountsByRole(String role);

    /**
     * Assigns a wallet address to a user account
     *
     * @param userId The ID of the user account
     * @param walletAddress The blockchain wallet address to assign
     * @return Response with the updated user account details
     */
    UserAccountResponse assignWalletAddress(String userId, String walletAddress);

    /**
     * Generates a new API key for a user account
     *
     * @param userId The ID of the user account
     * @return Response with the new API key
     */
    UserAccountResponse generateApiKey(String userId);
}
