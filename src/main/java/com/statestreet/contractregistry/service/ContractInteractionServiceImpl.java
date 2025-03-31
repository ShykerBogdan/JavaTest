package com.statestreet.contractregistry.service;

import com.statestreet.contractregistry.dto.ContractInteractionRequest;
import com.statestreet.contractregistry.dto.ContractInteractionResponse;
import com.statestreet.contractregistry.entity.ContractInteraction;
import com.statestreet.contractregistry.entity.ContractRegistry;
import com.statestreet.contractregistry.exception.ResourceNotFoundException;
import com.statestreet.contractregistry.repository.ContractInteractionRepository;
import com.statestreet.contractregistry.repository.ContractRegistryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementation of the ContractInteractionService interface.
 * Handles interactions with deployed smart contracts on the blockchain.
 */
@Service
public class ContractInteractionServiceImpl implements ContractInteractionService {

    private static final Logger log = LoggerFactory.getLogger(ContractInteractionServiceImpl.class);
    
    private final ContractInteractionRepository interactionRepository;
    private final ContractRegistryRepository contractRegistryRepository;
    
    /**
     * Constructor for dependency injection
     */
    public ContractInteractionServiceImpl(
            ContractInteractionRepository interactionRepository,
            ContractRegistryRepository contractRegistryRepository) {
        this.interactionRepository = interactionRepository;
        this.contractRegistryRepository = contractRegistryRepository;
    }

    @Override
    @Transactional
    public ContractInteractionResponse executeTransaction(ContractInteractionRequest request) {
        log.info("Executing transaction on contract: {} function: {}", 
                request.getContractAddress(), request.getFunctionName());
        
        // Verify contract exists and is whitelisted
        ContractRegistry registry = contractRegistryRepository.findByContractAddress(request.getContractAddress())
                .orElseThrow(() -> new ResourceNotFoundException("Contract not found: " + request.getContractAddress()));
        
        if (!registry.isWhitelisted()) {
            log.error("Attempted to interact with non-whitelisted contract: {}", request.getContractAddress());
            throw new IllegalStateException("Contract is not whitelisted for interactions: " + request.getContractAddress());
        }
        
        // Create interaction record
        String interactionId = generateInteractionId();
        ContractInteraction interaction = ContractInteraction.builder()
                .interactionId(interactionId)
                .contractAddress(request.getContractAddress())
                .functionName(request.getFunctionName())
                .functionParams(request.getFunctionParams())
                .status("PENDING")
                .initiatedBy(request.getInitiatedBy())
                .appId(request.getAppId())
                .build();
        
        ContractInteraction savedInteraction = interactionRepository.save(interaction);
        log.info("Interaction recorded with ID: {}", interactionId);
        
        try {
            // In a real implementation, this would interact with a blockchain node
            // For this example, we'll simulate a successful transaction
            String txHash = "0x" + UUID.randomUUID().toString().replace("-", "");
            long gasUsed = (long) (Math.random() * 1000000);
            
            // Update interaction with success
            savedInteraction.setStatus("COMPLETED");
            savedInteraction.setTransactionHash(txHash);
            savedInteraction.setGasUsed(gasUsed);
            savedInteraction.setCompletedAt(LocalDateTime.now());
            
            ContractInteraction updatedInteraction = interactionRepository.save(savedInteraction);
            log.info("Transaction completed with hash: {}", txHash);
            
            return createInteractionResponse(updatedInteraction);
        } catch (Exception e) {
            log.error("Error executing transaction: {}", e.getMessage(), e);
            
            // Update with error status
            savedInteraction.setStatus("FAILED");
            savedInteraction.setErrorMessage(e.getMessage());
            ContractInteraction updatedInteraction = interactionRepository.save(savedInteraction);
            
            // Create a response with error details instead of throwing exception
            ContractInteractionResponse errorResponse = createInteractionResponse(updatedInteraction);
            return errorResponse;
        }
    }

    @Override
    public ContractInteractionResponse queryContract(ContractInteractionRequest request) {
        log.info("Querying contract: {} function: {}", 
                request.getContractAddress(), request.getFunctionName());
        
        // Verify contract exists
        contractRegistryRepository.findByContractAddress(request.getContractAddress())
                .orElseThrow(() -> new ResourceNotFoundException("Contract not found: " + request.getContractAddress()));
        
        // Create interaction record for the query
        String interactionId = generateInteractionId();
        ContractInteraction interaction = ContractInteraction.builder()
                .interactionId(interactionId)
                .contractAddress(request.getContractAddress())
                .functionName(request.getFunctionName())
                .functionParams(request.getFunctionParams())
                .status("PENDING")
                .initiatedBy(request.getInitiatedBy())
                .appId(request.getAppId())
                .build();
        
        ContractInteraction savedInteraction = interactionRepository.save(interaction);
        
        try {
            // In a real implementation, this would call a blockchain node to get data
            // For this example, we'll simulate a successful query
            String result = "{\"data\": \"Sample contract data for " + request.getFunctionName() + "\"}";
            
            // Update interaction with success
            savedInteraction.setStatus("COMPLETED");
            savedInteraction.setResult(result);
            savedInteraction.setCompletedAt(LocalDateTime.now());
            
            ContractInteraction updatedInteraction = interactionRepository.save(savedInteraction);
            log.info("Query completed successfully for interaction: {}", interactionId);
            
            return createInteractionResponse(updatedInteraction);
        } catch (Exception e) {
            log.error("Error querying contract: {}", e.getMessage(), e);
            
            // Update with error status
            savedInteraction.setStatus("FAILED");
            savedInteraction.setErrorMessage(e.getMessage());
            ContractInteraction updatedInteraction = interactionRepository.save(savedInteraction);
            
            // Create a response with error details instead of throwing exception
            ContractInteractionResponse errorResponse = createInteractionResponse(updatedInteraction);
            return errorResponse;
        }
    }

    @Override
    public ContractInteractionResponse getInteractionStatus(String interactionId) {
        log.info("Getting status for interaction: {}", interactionId);
        
        ContractInteraction interaction = findInteractionById(interactionId);
        return createInteractionResponse(interaction);
    }

    @Override
    public List<ContractInteractionResponse> getContractInteractions(String contractAddress) {
        log.info("Getting all interactions for contract: {}", contractAddress);
        
        // Verify contract exists
        contractRegistryRepository.findByContractAddress(contractAddress)
                .orElseThrow(() -> new ResourceNotFoundException("Contract not found: " + contractAddress));
        
        List<ContractInteraction> interactions = interactionRepository.findByContractAddress(contractAddress);
        return interactions.stream()
                .map(this::createInteractionResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ContractInteractionResponse> getUserInteractions(String userId) {
        log.info("Getting all interactions initiated by user: {}", userId);
        
        List<ContractInteraction> interactions = interactionRepository.findByInitiatedBy(userId);
        return interactions.stream()
                .map(this::createInteractionResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ContractInteractionResponse> getAppInteractions(String appId) {
        log.info("Getting all interactions initiated by app: {}", appId);
        
        List<ContractInteraction> interactions = interactionRepository.findByAppId(appId);
        return interactions.stream()
                .map(this::createInteractionResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * Helper method to find an interaction by its ID
     */
    private ContractInteraction findInteractionById(String interactionId) {
        return interactionRepository.findByInteractionId(interactionId)
                .orElseThrow(() -> {
                    log.error("Interaction not found with ID: {}", interactionId);
                    return new ResourceNotFoundException("Interaction not found with ID: " + interactionId);
                });
    }
    
    /**
     * Helper method to create an interaction response from entity
     */
    private ContractInteractionResponse createInteractionResponse(ContractInteraction interaction) {
        return ContractInteractionResponse.builder()
                .interactionId(interaction.getInteractionId())
                .contractAddress(interaction.getContractAddress())
                .functionName(interaction.getFunctionName())
                .functionParams(interaction.getFunctionParams())
                .transactionHash(interaction.getTransactionHash())
                .status(interaction.getStatus())
                .result(interaction.getResult())
                .errorMessage(interaction.getErrorMessage())
                .gasUsed(interaction.getGasUsed())
                .completedAt(interaction.getCompletedAt())
                .build();
    }
    
    /**
     * Helper method to generate a unique interaction ID
     */
    private String generateInteractionId() {
        return "INT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
