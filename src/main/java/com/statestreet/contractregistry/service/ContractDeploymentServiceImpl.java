package com.statestreet.contractregistry.service;

import com.statestreet.contractregistry.dto.DeploymentRequest;
import com.statestreet.contractregistry.dto.DeploymentResponse;
import com.statestreet.contractregistry.entity.ContractDeployment;
import com.statestreet.contractregistry.entity.ContractLibrary;
import com.statestreet.contractregistry.entity.ContractRegistry;
import com.statestreet.contractregistry.exception.DeploymentException;
import com.statestreet.contractregistry.exception.ResourceNotFoundException;
import com.statestreet.contractregistry.repository.ContractDeploymentRepository;
import com.statestreet.contractregistry.repository.ContractLibraryRepository;
import com.statestreet.contractregistry.repository.ContractRegistryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Implementation of the ContractDeploymentService interface.
 * Handles logic for deploying smart contracts to blockchain networks.
 */
@Service
public class ContractDeploymentServiceImpl implements ContractDeploymentService {

    private static final Logger log = LoggerFactory.getLogger(ContractDeploymentServiceImpl.class);
    
    private final ContractDeploymentRepository deploymentRepository;
    private final ContractLibraryRepository contractLibraryRepository;
    private final ContractRegistryRepository contractRegistryRepository;
    
    /**
     * Constructor for dependency injection
     */
    public ContractDeploymentServiceImpl(
            ContractDeploymentRepository deploymentRepository,
            ContractLibraryRepository contractLibraryRepository,
            ContractRegistryRepository contractRegistryRepository) {
        this.deploymentRepository = deploymentRepository;
        this.contractLibraryRepository = contractLibraryRepository;
        this.contractRegistryRepository = contractRegistryRepository;
    }

    @Override
    @Transactional
    public DeploymentResponse initiateDeployment(DeploymentRequest request) {
        log.info("Initiating deployment for contract: {}", request.getContractName());
        
        // Validate contract exists in library
        if (!contractLibraryRepository.existsByName(request.getContractName())) {
            log.error("Contract {} not found in library", request.getContractName());
            throw new ResourceNotFoundException("Contract template not found: " + request.getContractName());
        }
        
        // Create deployment record
        String requestId = generateRequestId();
        ContractDeployment deployment = ContractDeployment.builder()
                .requestId(requestId)
                .contractName(request.getContractName())
                .network(request.getNetwork())
                .deploymentParams(request.getDeploymentParams())
                .status("PENDING_APPROVAL")
                .requesterId(request.getRequesterId())
                .build();
        
        ContractDeployment savedDeployment = deploymentRepository.save(deployment);
        log.info("Deployment request created with ID: {}", requestId);
        
        return createDeploymentResponse(savedDeployment);
    }

    @Override
    @Transactional
    public DeploymentResponse approveDeployment(String requestId) {
        log.info("Approving deployment with ID: {}", requestId);
        
        ContractDeployment deployment = findDeploymentByRequestId(requestId);
        
        if (!"PENDING_APPROVAL".equals(deployment.getStatus())) {
            log.error("Cannot approve deployment that is not in PENDING_APPROVAL state. Current state: {}", deployment.getStatus());
            throw new DeploymentException("Cannot approve deployment in state: " + deployment.getStatus());
        }
        
        deployment.setStatus("APPROVED");
        deployment.setApprovedAt(LocalDateTime.now());
        
        ContractDeployment updatedDeployment = deploymentRepository.save(deployment);
        log.info("Deployment approved: {}", requestId);
        
        // Asynchronously trigger the actual deployment
        // In a real implementation, this might be handled by a message queue or a separate thread
        deployContract(requestId);
        return createDeploymentResponse(updatedDeployment);
    }

    @Override
    @Transactional
    public DeploymentResponse deployContract(String requestId) {
        log.info("Deploying contract for request ID: {}", requestId);
        
        ContractDeployment deployment = findDeploymentByRequestId(requestId);
        
        if (!"APPROVED".equals(deployment.getStatus())) {
            log.error("Cannot deploy a contract that is not APPROVED. Current state: {}", deployment.getStatus());
            throw new DeploymentException("Cannot deploy contract in state: " + deployment.getStatus());
        }
        
        // Update status to deploying
        deployment.setStatus("DEPLOYING");
        deploymentRepository.save(deployment);
        
        try {
            // Get contract library details
            ContractLibrary contractLibrary = contractLibraryRepository.findByName(deployment.getContractName());
            if (contractLibrary == null) {
                throw new ResourceNotFoundException("Contract not found in library: " + deployment.getContractName());
            }
            
            // In a real implementation, this would interact with a blockchain node or Taurus Protect API
            // For this example, we'll simulate a successful deployment
            String contractAddress = "0x" + UUID.randomUUID().toString().replace("-", "").substring(0, 40);
            String txHash = "0x" + UUID.randomUUID().toString().replace("-", "");
            
            // Update deployment with success
            deployment.setStatus("DEPLOYED");
            deployment.setContractAddress(contractAddress);
            deployment.setTransactionHash(txHash);
            deployment.setDeployedAt(LocalDateTime.now());
            
            // Save the deployed contract in the registry
            ContractRegistry registry = ContractRegistry.builder()
                    .contractAddress(contractAddress)
                    .contractName(deployment.getContractName())
                    .version(contractLibrary.getVersion())
                    .network(deployment.getNetwork())
                    .owner(deployment.getRequesterId())
                    .abi(contractLibrary.getAbi())
                    .deploymentTimestamp(LocalDateTime.now())
                    .whitelisted(false)
                    .build();
            
            contractRegistryRepository.save(registry);
            ContractDeployment savedDeployment = deploymentRepository.save(deployment);
            
            log.info("Contract deployed successfully at address: {}", contractAddress);
            return createDeploymentResponse(savedDeployment);
        } catch (Exception e) {
            log.error("Error deploying contract: {}", e.getMessage(), e);
            
            // Update with error status
            deployment.setStatus("DEPLOYMENT_FAILED");
            ContractDeployment savedDeployment = deploymentRepository.save(deployment);
            
            // Create a response with error details instead of throwing exception
            DeploymentResponse errorResponse = createDeploymentResponse(savedDeployment);
            errorResponse.setErrorMessage("Failed to deploy contract: " + e.getMessage());
            return errorResponse;
        }
    }

    @Override
    @Transactional
    public DeploymentResponse whitelistContract(String requestId) {
        log.info("Whitelisting contract for deployment ID: {}", requestId);
        
        ContractDeployment deployment = findDeploymentByRequestId(requestId);
        
        if (!"DEPLOYED".equals(deployment.getStatus())) {
            log.error("Cannot whitelist a contract that is not DEPLOYED. Current state: {}", deployment.getStatus());
            throw new DeploymentException("Cannot whitelist contract in state: " + deployment.getStatus());
        }
        
        // Update contract in registry
        ContractRegistry registry = contractRegistryRepository.findByContractAddress(deployment.getContractAddress())
                .orElseThrow(() -> new ResourceNotFoundException("Contract not found in registry: " + deployment.getContractAddress()));
        
        registry.setWhitelisted(true);
        registry.setWhitelistTimestamp(LocalDateTime.now());
        contractRegistryRepository.save(registry);
        
        // Update deployment status
        deployment.setStatus("WHITELISTED");
        ContractDeployment savedDeployment = deploymentRepository.save(deployment);
        
        log.info("Contract whitelisted successfully: {}", deployment.getContractAddress());
        return createDeploymentResponse(savedDeployment);
    }

    @Override
    public DeploymentResponse getDeploymentStatus(String requestId) {
        log.info("Getting deployment status for ID: {}", requestId);
        
        ContractDeployment deployment = findDeploymentByRequestId(requestId);
        return createDeploymentResponse(deployment);
    }

    @Override
    @Transactional
    public DeploymentResponse cancelDeployment(String requestId) {
        log.info("Cancelling deployment with ID: {}", requestId);
        
        ContractDeployment deployment = findDeploymentByRequestId(requestId);
        
        if (!("PENDING_APPROVAL".equals(deployment.getStatus()) || "APPROVED".equals(deployment.getStatus()))) {
            log.error("Cannot cancel deployment in state: {}", deployment.getStatus());
            throw new DeploymentException("Cannot cancel deployment in state: " + deployment.getStatus());
        }
        
        deployment.setStatus("CANCELLED");
        ContractDeployment savedDeployment = deploymentRepository.save(deployment);
        
        log.info("Deployment cancelled: {}", requestId);
        return createDeploymentResponse(savedDeployment);
    }
    
    /**
     * Helper method to find a deployment by request ID
     */
    private ContractDeployment findDeploymentByRequestId(String requestId) {
        return deploymentRepository.findByRequestId(requestId)
                .orElseThrow(() -> {
                    log.error("Deployment not found with ID: {}", requestId);
                    return new ResourceNotFoundException("Deployment not found with ID: " + requestId);
                });
    }
    
    /**
     * Helper method to create a deployment response from entity
     */
    private DeploymentResponse createDeploymentResponse(ContractDeployment deployment) {
        return DeploymentResponse.builder()
                .requestId(deployment.getRequestId())
                .contractName(deployment.getContractName())
                .status(deployment.getStatus())
                .contractAddress(deployment.getContractAddress())
                .transactionHash(deployment.getTransactionHash())
                .requestedAt(deployment.getRequestedAt())
                .approvedAt(deployment.getApprovedAt())
                .deployedAt(deployment.getDeployedAt())
                .build();
    }
    
    /**
     * Helper method to generate a unique request ID
     */
    private String generateRequestId() {
        return "DEP-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
