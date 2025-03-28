package com.statestreet.contractregistry.service;

import com.statestreet.contractregistry.dto.DeploymentRequest;
import com.statestreet.contractregistry.dto.DeploymentResponse;
import com.statestreet.contractregistry.model.SmartContractDeployment;
import com.statestreet.contractregistry.statemachine.DeploymentState;

public interface DeploymentService {
    
    /**
     * Initiates the smart contract deployment process
     */
    DeploymentResponse initiateDeployment(DeploymentRequest request);
    
    /**
     * Approves a pending deployment request
     */
    DeploymentResponse approveDeployment(String requestId);
    
    /**
     * Whitelists a deployed contract
     */
    DeploymentResponse whitelistContract(String requestId);
    
    /**
     * Gets the status of a deployment request
     */
    DeploymentResponse getDeploymentStatus(String requestId);
    
    /**
     * Handles state machine events and transitions
     */
    boolean sendEvent(String requestId, DeploymentState currentState, Object event);
    
    /**
     * Gets a smart contract deployment entity by request ID
     */
    SmartContractDeployment getDeploymentByRequestId(String requestId);
}
