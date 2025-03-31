package com.statestreet.contractregistry.service;

import com.statestreet.contractregistry.dto.ContractInteractionRequest;
import com.statestreet.contractregistry.dto.ContractInteractionResponse;

import java.util.List;

/**
 * Service interface for smart contract interaction operations.
 * Handles logic for interacting with deployed smart contracts, such as calling functions and querying data.
 */
public interface ContractInteractionService {

    /**
     * Executes a transaction on a deployed smart contract
     *
     * @param request The interaction request containing contract address, function, and parameters
     * @return Response with the transaction details and status
     */
    ContractInteractionResponse executeTransaction(ContractInteractionRequest request);

    /**
     * Queries data from a smart contract (read-only operation)
     *
     * @param request The interaction request containing contract address, function, and parameters
     * @return Response with the query results
     */
    ContractInteractionResponse queryContract(ContractInteractionRequest request);

    /**
     * Gets the status of a previously submitted transaction
     *
     * @param interactionId The unique ID of the interaction
     * @return Current status of the interaction
     */
    ContractInteractionResponse getInteractionStatus(String interactionId);

    /**
     * Retrieves all interactions for a specific contract
     *
     * @param contractAddress The address of the deployed contract
     * @return List of interactions with the contract
     */
    List<ContractInteractionResponse> getContractInteractions(String contractAddress);

    /**
     * Retrieves all interactions initiated by a specific user
     *
     * @param userId The ID of the user
     * @return List of interactions initiated by the user
     */
    List<ContractInteractionResponse> getUserInteractions(String userId);

    /**
     * Retrieves all interactions initiated by a specific application
     *
     * @param appId The ID of the application
     * @return List of interactions initiated by the application
     */
    List<ContractInteractionResponse> getAppInteractions(String appId);
}
