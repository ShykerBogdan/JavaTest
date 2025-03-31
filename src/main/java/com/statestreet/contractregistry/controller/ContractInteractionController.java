package com.statestreet.contractregistry.controller;

import com.statestreet.contractregistry.dto.ContractInteractionRequest;
import com.statestreet.contractregistry.dto.ContractInteractionResponse;
import com.statestreet.contractregistry.service.ContractInteractionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * REST controller for smart contract interaction operations.
 * Handles API endpoints related to interacting with deployed smart contracts.
 */
@RestController
@RequestMapping("/api/contracts/interaction")
public class ContractInteractionController {

    private static final Logger log = LoggerFactory.getLogger(ContractInteractionController.class);

    private final ContractInteractionService interactionService;

    /**
     * Constructor for dependency injection
     */
    public ContractInteractionController(ContractInteractionService interactionService) {
        this.interactionService = interactionService;
    }

    /**
     * Endpoint to execute a transaction on a smart contract
     * 
     * @param request The interaction request containing contract address, function, and parameters
     * @return Response with the transaction details and status
     */
    @PostMapping("/transaction")
    public ResponseEntity<ContractInteractionResponse> executeTransaction(@Valid @RequestBody ContractInteractionRequest request) {
        log.info("Received request to execute transaction on contract: {} function: {}", 
                request.getContractAddress(), request.getFunctionName());
        ContractInteractionResponse response = interactionService.executeTransaction(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Endpoint to query data from a smart contract (read-only operation)
     * 
     * @param request The interaction request containing contract address, function, and parameters
     * @return Response with the query results
     */
    @PostMapping("/query")
    public ResponseEntity<ContractInteractionResponse> queryContract(@Valid @RequestBody ContractInteractionRequest request) {
        log.info("Received request to query contract: {} function: {}", 
                request.getContractAddress(), request.getFunctionName());
        ContractInteractionResponse response = interactionService.queryContract(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint to get the status of a previously submitted transaction
     * 
     * @param interactionId The unique ID of the interaction
     * @return Current status of the interaction
     */
    @GetMapping("/{interactionId}")
    public ResponseEntity<ContractInteractionResponse> getInteractionStatus(@PathVariable String interactionId) {
        log.info("Received request to get status of interaction with ID: {}", interactionId);
        ContractInteractionResponse response = interactionService.getInteractionStatus(interactionId);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint to get all interactions for a specific contract
     * 
     * @param contractAddress The address of the deployed contract
     * @return List of interactions with the contract
     */
    @GetMapping("/contract/{contractAddress}")
    public ResponseEntity<List<ContractInteractionResponse>> getContractInteractions(@PathVariable String contractAddress) {
        log.info("Received request to get all interactions for contract: {}", contractAddress);
        List<ContractInteractionResponse> interactions = interactionService.getContractInteractions(contractAddress);
        return ResponseEntity.ok(interactions);
    }

    /**
     * Endpoint to get all interactions initiated by a specific user
     * 
     * @param userId The ID of the user
     * @return List of interactions initiated by the user
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ContractInteractionResponse>> getUserInteractions(@PathVariable String userId) {
        log.info("Received request to get all interactions initiated by user: {}", userId);
        List<ContractInteractionResponse> interactions = interactionService.getUserInteractions(userId);
        return ResponseEntity.ok(interactions);
    }

    /**
     * Endpoint to get all interactions initiated by a specific application
     * 
     * @param appId The ID of the application
     * @return List of interactions initiated by the application
     */
    @GetMapping("/app/{appId}")
    public ResponseEntity<List<ContractInteractionResponse>> getAppInteractions(@PathVariable String appId) {
        log.info("Received request to get all interactions initiated by app: {}", appId);
        List<ContractInteractionResponse> interactions = interactionService.getAppInteractions(appId);
        return ResponseEntity.ok(interactions);
    }
}
