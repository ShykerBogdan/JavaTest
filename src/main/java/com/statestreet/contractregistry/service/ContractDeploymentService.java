package com.statestreet.contractregistry.service;

import com.statestreet.contractregistry.dto.DeploymentRequest;
import com.statestreet.contractregistry.dto.DeploymentResponse;

/**
 * Service interface for smart contract deployment operations.
 * Handles logic for deploying smart contracts and interacts with Taurus Protect API for signing transactions.
 */
public interface ContractDeploymentService {

    /**
     * Initiates a new contract deployment request
     *
     * @param request The deployment request containing contract details and parameters
     * @return Response with the deployment status and request ID
     */
    DeploymentResponse initiateDeployment(DeploymentRequest request);

    /**
     * Approves a pending deployment request
     *
     * @param requestId The unique ID of the deployment request to approve
     * @return Updated deployment response with the new status
     */
    DeploymentResponse approveDeployment(String requestId);

    /**
     * Deploys the contract to the blockchain
     *
     * @param requestId The unique ID of the approved deployment request
     * @return Deployment response with the transaction details
     */
    DeploymentResponse deployContract(String requestId);

    /**
     * Whitelists a deployed contract for broader use
     *
     * @param requestId The unique ID of the deployment request
     * @return Deployment response with the updated whitelist status
     */
    DeploymentResponse whitelistContract(String requestId);

    /**
     * Retrieves the current status of a deployment request
     *
     * @param requestId The unique ID of the deployment request
     * @return Deployment response with the current status and details
     */
    DeploymentResponse getDeploymentStatus(String requestId);

    /**
     * Cancels a pending deployment request
     *
     * @param requestId The unique ID of the deployment request to cancel
     * @return Deployment response with the updated status
     */
    DeploymentResponse cancelDeployment(String requestId);
}
