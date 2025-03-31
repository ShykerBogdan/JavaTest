package com.statestreet.contractregistry.controller;

import com.statestreet.contractregistry.dto.DeploymentRequest;
import com.statestreet.contractregistry.dto.DeploymentResponse;
import com.statestreet.contractregistry.service.ContractDeploymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * REST controller for smart contract deployment operations.
 * Handles API endpoints related to deploying smart contracts.
 */
@RestController
@RequestMapping("/api/contracts/deployment")
public class ContractDeploymentController {

    private static final Logger log = LoggerFactory.getLogger(ContractDeploymentController.class);

    private final ContractDeploymentService deploymentService;

    /**
     * Constructor for dependency injection
     */
    public ContractDeploymentController(ContractDeploymentService deploymentService) {
        this.deploymentService = deploymentService;
    }

    /**
     * Endpoint to initiate a smart contract deployment
     * 
     * @param request The deployment request containing contract details and parameters
     * @return Response with the deployment status and request ID
     */
    @PostMapping
    public ResponseEntity<DeploymentResponse> initiateDeployment(@Valid @RequestBody DeploymentRequest request) {
        log.info("Received request to deploy smart contract: {}", request.getContractName());
        DeploymentResponse response = deploymentService.initiateDeployment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Endpoint to approve a deployment request
     * 
     * @param requestId The unique ID of the deployment request to approve
     * @return Response with the updated status
     */
    @PostMapping("/{requestId}/approve")
    public ResponseEntity<DeploymentResponse> approveDeployment(@PathVariable String requestId) {
        log.info("Received request to approve deployment with ID: {}", requestId);
        DeploymentResponse response = deploymentService.approveDeployment(requestId);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint to deploy a contract after approval
     * 
     * @param requestId The unique ID of the approved deployment request
     * @return Response with the transaction details
     */
    @PostMapping("/{requestId}/execute")
    public ResponseEntity<DeploymentResponse> executeDeployment(@PathVariable String requestId) {
        log.info("Received request to execute deployment with ID: {}", requestId);
        DeploymentResponse response = deploymentService.deployContract(requestId);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint to whitelist a deployed contract
     * 
     * @param requestId The unique ID of the deployment request
     * @return Response with the updated whitelist status
     */
    @PostMapping("/{requestId}/whitelist")
    public ResponseEntity<DeploymentResponse> whitelistContract(@PathVariable String requestId) {
        log.info("Received request to whitelist contract with ID: {}", requestId);
        DeploymentResponse response = deploymentService.whitelistContract(requestId);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint to get the status of a deployment request
     * 
     * @param requestId The unique ID of the deployment request
     * @return Current status of the deployment
     */
    @GetMapping("/{requestId}")
    public ResponseEntity<DeploymentResponse> getDeploymentStatus(@PathVariable String requestId) {
        log.info("Received request to get status of deployment with ID: {}", requestId);
        DeploymentResponse response = deploymentService.getDeploymentStatus(requestId);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint to cancel a pending deployment request
     * 
     * @param requestId The unique ID of the deployment request to cancel
     * @return Response with the updated status
     */
    @PostMapping("/{requestId}/cancel")
    public ResponseEntity<DeploymentResponse> cancelDeployment(@PathVariable String requestId) {
        log.info("Received request to cancel deployment with ID: {}", requestId);
        DeploymentResponse response = deploymentService.cancelDeployment(requestId);
        return ResponseEntity.ok(response);
    }
}
