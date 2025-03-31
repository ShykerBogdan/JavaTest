package com.statestreet.contractregistry.repository;

import com.statestreet.contractregistry.entity.ContractDeployment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing contract deployment data.
 * Handles storage and retrieval of smart contract deployment requests and their statuses.
 */
@Repository
public interface ContractDeploymentRepository extends JpaRepository<ContractDeployment, Long> {
    
    /**
     * Find a deployment request by its unique request ID
     * 
     * @param requestId The unique identifier for the deployment request
     * @return Optional of the deployment if found
     */
    Optional<ContractDeployment> findByRequestId(String requestId);
    
    /**
     * Find all deployment requests for a specific contract name
     * 
     * @param contractName The name of the contract
     * @return List of deployment requests for the given contract name
     */
    List<ContractDeployment> findByContractName(String contractName);
    
    /**
     * Find all deployment requests with a specific status
     * 
     * @param status The status to filter by
     * @return List of deployment requests with the given status
     */
    List<ContractDeployment> findByStatus(String status);
    
    /**
     * Find all deployment requests initiated by a specific user
     * 
     * @param requesterId The ID of the requester
     * @return List of deployment requests initiated by the given user
     */
    List<ContractDeployment> findByRequesterId(String requesterId);
    
    /**
     * Find all deployment requests for a specific contract address
     * 
     * @param contractAddress The blockchain address of the deployed contract
     * @return List of deployment requests for the given contract address
     */
    List<ContractDeployment> findByContractAddress(String contractAddress);
    
    /**
     * Check if a deployment request exists with the given request ID
     * 
     * @param requestId The request ID to check
     * @return True if exists, false otherwise
     */
    boolean existsByRequestId(String requestId);
}
