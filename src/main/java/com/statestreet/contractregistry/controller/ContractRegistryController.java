package com.statestreet.contractregistry.controller;

import com.statestreet.contractregistry.dto.DeploymentRequest;
import com.statestreet.contractregistry.dto.DeploymentResponse;
import com.statestreet.contractregistry.service.DeploymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/contracts")
public class ContractRegistryController {

    private static final Logger log = LoggerFactory.getLogger(ContractRegistryController.class);

    private final DeploymentService deploymentService;

    public ContractRegistryController(DeploymentService deploymentService) {
        this.deploymentService = deploymentService;
    }

    /**
     * Endpoint to initiate a smart contract deployment
     */
    @PostMapping("/deploy")
    public ResponseEntity<DeploymentResponse> deploySmartContract(@Valid @RequestBody DeploymentRequest request) {
        log.info("Received request to deploy smart contract: {}", request.getContractName());
        DeploymentResponse response = deploymentService.initiateDeployment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Endpoint to approve a deployment request
     */
    @PostMapping("/{requestId}/approve")
    public ResponseEntity<DeploymentResponse> approveDeployment(@PathVariable String requestId) {
        log.info("Received request to approve deployment with ID: {}", requestId);
        DeploymentResponse response = deploymentService.approveDeployment(requestId);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint to whitelist a deployed contract
     */
    @PostMapping("/{requestId}/whitelist")
    public ResponseEntity<DeploymentResponse> whitelistContract(@PathVariable String requestId) {
        log.info("Received request to whitelist contract with ID: {}", requestId);
        DeploymentResponse response = deploymentService.whitelistContract(requestId);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint to get the status of a deployment request
     */
    @GetMapping("/{requestId}/status")
    public ResponseEntity<DeploymentResponse> getDeploymentStatus(@PathVariable String requestId) {
        log.info("Received request to get status of deployment with ID: {}", requestId);
        DeploymentResponse response = deploymentService.getDeploymentStatus(requestId);
        return ResponseEntity.ok(response);
    }
}
