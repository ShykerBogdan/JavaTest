package com.statestreet.contractregistry.repository;

import com.statestreet.contractregistry.entity.ContractInteraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing contract interaction data.
 * Handles storage and retrieval of smart contract interactions and their results.
 */
@Repository
public interface ContractInteractionRepository extends JpaRepository<ContractInteraction, Long> {
    
    /**
     * Find an interaction by its unique ID
     * 
     * @param interactionId The unique identifier for the interaction
     * @return Optional of the interaction if found
     */
    Optional<ContractInteraction> findByInteractionId(String interactionId);
    
    /**
     * Find all interactions with a specific contract
     * 
     * @param contractAddress The blockchain address of the contract
     * @return List of interactions with the given contract
     */
    List<ContractInteraction> findByContractAddress(String contractAddress);
    
    /**
     * Find all interactions that called a specific function
     * 
     * @param functionName The name of the function that was called
     * @return List of interactions calling the given function
     */
    List<ContractInteraction> findByFunctionName(String functionName);
    
    /**
     * Find all interactions with a specific status
     * 
     * @param status The status to filter by
     * @return List of interactions with the given status
     */
    List<ContractInteraction> findByStatus(String status);
    
    /**
     * Find all interactions initiated by a specific user
     * 
     * @param initiatedBy The ID of the user who initiated the interaction
     * @return List of interactions initiated by the given user
     */
    List<ContractInteraction> findByInitiatedBy(String initiatedBy);
    
    /**
     * Find all interactions initiated by a specific application
     * 
     * @param appId The ID of the application that initiated the interaction
     * @return List of interactions initiated by the given application
     */
    List<ContractInteraction> findByAppId(String appId);
    
    /**
     * Check if an interaction exists with the given interaction ID
     * 
     * @param interactionId The interaction ID to check
     * @return True if exists, false otherwise
     */
    boolean existsByInteractionId(String interactionId);
}
