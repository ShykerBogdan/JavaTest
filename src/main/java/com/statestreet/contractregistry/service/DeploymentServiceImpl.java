package com.statestreet.contractregistry.service;

import com.statestreet.contractregistry.client.HashSigningServiceClient;
import com.statestreet.contractregistry.client.TaurusProtectClient;
import com.statestreet.contractregistry.client.TokenRegistryClient;
import com.statestreet.contractregistry.dto.DeploymentRequest;
import com.statestreet.contractregistry.dto.DeploymentResponse;
import com.statestreet.contractregistry.model.SmartContractDeployment;
import com.statestreet.contractregistry.repository.SmartContractDeploymentRepository;
import com.statestreet.contractregistry.statemachine.ContractDeployment.DeploymentEvent;
import com.statestreet.contractregistry.statemachine.ContractDeployment.DeploymentState;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class DeploymentServiceImpl implements DeploymentService {

    private static final Logger log = LoggerFactory.getLogger(DeploymentServiceImpl.class);

    private static final String DEPLOYMENT_ID_HEADER = "deploymentId";

    private final StateMachineFactory<DeploymentState, DeploymentEvent> stateMachineFactory;
    private final SmartContractDeploymentRepository deploymentRepository;
    private final TaurusProtectClient taurusProtectClient;
    private final HashSigningServiceClient hashServiceClient;
    private final TokenRegistryClient tokenRegistryClient;

    public DeploymentServiceImpl(StateMachineFactory<DeploymentState, DeploymentEvent> stateMachineFactory,
            SmartContractDeploymentRepository deploymentRepository,
            TaurusProtectClient taurusProtectClient,
            HashSigningServiceClient hashServiceClient,
            TokenRegistryClient tokenRegistryClient) {
        this.stateMachineFactory = stateMachineFactory;
        this.deploymentRepository = deploymentRepository;
        this.taurusProtectClient = taurusProtectClient;
        this.hashServiceClient = hashServiceClient;
        this.tokenRegistryClient = tokenRegistryClient;
    }

    @Override
    @Transactional
    public DeploymentResponse initiateDeployment(DeploymentRequest request) {
        log.info("Initiating smart contract deployment for contract: {}", request.getContractName());

        // Create a new deployment entity
        SmartContractDeployment deployment = SmartContractDeployment.builder()
                .contractName(request.getContractName())
                .contractBytecode(request.getContractBytecode())
                .constructorArgs(request.getConstructorArgs())
                .currentState(DeploymentState.INITIAL)
                .build();

        // Save to repository
        SmartContractDeployment savedDeployment = deploymentRepository.save(deployment);

        try {
            // Start authentication process
            String authToken = taurusProtectClient.getAuthToken();
            savedDeployment.setAuthToken(authToken);
            savedDeployment = deploymentRepository.save(savedDeployment);

            // Update state to AUTHENTICATED
            sendEvent(savedDeployment.getId().toString(), DeploymentState.INITIAL,
                    DeploymentEvent.AUTHENTICATION_SUCCESS);

            // Request deployment
            String requestId = taurusProtectClient.deploySmartContract(
                    authToken,
                    request.getContractBytecode(),
                    request.getContractName(),
                    request.getConstructorArgs());

            savedDeployment.setRequestId(requestId);
            savedDeployment = deploymentRepository.save(savedDeployment);

            // Update state to DEPLOY_REQUESTED
            sendEvent(savedDeployment.getId().toString(), DeploymentState.AUTHENTICATED,
                    DeploymentEvent.DEPLOYMENT_REQUEST_SUCCESS);

            // Prepare response
            return buildDeploymentResponse(savedDeployment);

        } catch (Exception e) {
            log.error("Failed to initiate deployment", e);
            savedDeployment.setErrorMessage("Failed to initiate deployment: " + e.getMessage());
            savedDeployment.setCurrentState(DeploymentState.ERROR);
            deploymentRepository.save(savedDeployment);

            throw new RuntimeException("Failed to initiate smart contract deployment", e);
        }
    }

    @Override
    @Transactional
    public DeploymentResponse approveDeployment(String requestId) {
        log.info("Approving deployment for request ID: {}", requestId);

        SmartContractDeployment deployment = getDeploymentByRequestId(requestId);

        try {
            // Update state to APPROVAL_PENDING
            sendEvent(deployment.getId().toString(), deployment.getCurrentState(), DeploymentEvent.REQUEST_APPROVAL);

            // Fetch request details including hash
            Map<String, Object> requestDetails = taurusProtectClient.getRequestDetails(
                    deployment.getAuthToken(),
                    requestId);

            String hash = (String) requestDetails.get("hash");
            String metadata = (String) requestDetails.get("metadata");

            deployment.setHashValue(hash);
            deployment = deploymentRepository.save(deployment);

            // Update state to HASH_RETRIEVED
            sendEvent(deployment.getId().toString(), DeploymentState.APPROVAL_PENDING, DeploymentEvent.HASH_FETCHED);

            // Sign the hash
            String signedHash = hashServiceClient.signHash(hash, metadata);
            deployment.setSignedHash(signedHash);
            deployment = deploymentRepository.save(deployment);

            // Update state to HASH_SIGNED
            sendEvent(deployment.getId().toString(), DeploymentState.HASH_RETRIEVED, DeploymentEvent.HASH_SIGNED);

            // Approve the deployment
            taurusProtectClient.approveDeployment(
                    deployment.getAuthToken(),
                    requestId,
                    signedHash);

            // Update state to DEPLOYMENT_APPROVED
            sendEvent(deployment.getId().toString(), DeploymentState.HASH_SIGNED, DeploymentEvent.DEPLOYMENT_APPROVED);

            // Check deployment status
            checkDeploymentStatus(deployment);

            return buildDeploymentResponse(deployment);

        } catch (Exception e) {
            log.error("Failed to approve deployment", e);
            deployment.setErrorMessage("Failed to approve deployment: " + e.getMessage());
            deployment.setCurrentState(DeploymentState.ERROR);
            deploymentRepository.save(deployment);

            throw new RuntimeException("Failed to approve smart contract deployment", e);
        }
    }

    @Override
    @Transactional
    public DeploymentResponse whitelistContract(String requestId) {
        log.info("Whitelisting contract for request ID: {}", requestId);

        SmartContractDeployment deployment = getDeploymentByRequestId(requestId);

        try {
            // Update state to WHITELIST_REQUESTED
            sendEvent(deployment.getId().toString(), deployment.getCurrentState(), DeploymentEvent.REQUEST_WHITELIST);

            // Fetch whitelist details
            Map<String, Object> whitelistDetails = taurusProtectClient.getWhitelistApprovalDetails(
                    deployment.getAuthToken(),
                    deployment.getWhitelistId());

            String whitelistHash = (String) whitelistDetails.get("hash");
            String whitelistMetadata = (String) whitelistDetails.get("metadata");

            deployment.setWhitelistHash(whitelistHash);
            deployment = deploymentRepository.save(deployment);

            // Update state to WHITELIST_HASH_RETRIEVED
            sendEvent(deployment.getId().toString(), DeploymentState.WHITELIST_REQUESTED,
                    DeploymentEvent.WHITELIST_HASH_FETCHED);

            // Sign the whitelist hash
            String signedWhitelistHash = hashServiceClient.signHash(whitelistHash, whitelistMetadata);
            deployment.setSignedWhitelistHash(signedWhitelistHash);
            deployment = deploymentRepository.save(deployment);

            // Update state to WHITELIST_HASH_SIGNED
            sendEvent(deployment.getId().toString(), DeploymentState.WHITELIST_HASH_RETRIEVED,
                    DeploymentEvent.WHITELIST_HASH_SIGNED);

            // Approve the whitelist
            taurusProtectClient.approveWhitelist(
                    deployment.getAuthToken(),
                    deployment.getWhitelistId(),
                    signedWhitelistHash);

            // Update state to WHITELIST_APPROVED
            sendEvent(deployment.getId().toString(), DeploymentState.WHITELIST_HASH_SIGNED,
                    DeploymentEvent.WHITELIST_APPROVED);

            // Register token with Token Registry
            boolean registered = tokenRegistryClient.registerToken(
                    deployment.getContractAddress(),
                    "{\"name\":\"" + deployment.getContractName() + "\"}");

            if (registered) {
                // Update state to TOKEN_REGISTERED
                sendEvent(deployment.getId().toString(), DeploymentState.WHITELIST_APPROVED,
                        DeploymentEvent.TOKEN_REGISTERED);

                // Mark as completed
                sendEvent(deployment.getId().toString(), DeploymentState.TOKEN_REGISTERED,
                        DeploymentEvent.REGISTER_TOKEN);
            }

            return buildDeploymentResponse(deployment);

        } catch (Exception e) {
            log.error("Failed to whitelist contract", e);
            deployment.setErrorMessage("Failed to whitelist contract: " + e.getMessage());
            deployment.setCurrentState(DeploymentState.ERROR);
            deploymentRepository.save(deployment);

            throw new RuntimeException("Failed to whitelist smart contract", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public DeploymentResponse getDeploymentStatus(String requestId) {
        log.info("Getting deployment status for request ID: {}", requestId);

        SmartContractDeployment deployment = getDeploymentByRequestId(requestId);

        // If the contract is in DEPLOYMENT_APPROVED state, check status to see if it's
        // deployed
        if (deployment.getCurrentState() == DeploymentState.DEPLOYMENT_APPROVED) {
            try {
                checkDeploymentStatus(deployment);
            } catch (Exception e) {
                log.error("Error checking deployment status", e);
            }
        }

        return buildDeploymentResponse(deployment);
    }

    @Override
    @Transactional
    public SmartContractDeployment getDeploymentByRequestId(String requestId) {
        return deploymentRepository.findByRequestId(requestId)
                .orElseThrow(() -> new RuntimeException("Deployment not found for request ID: " + requestId));
    }

    @Override
    @Transactional
    public boolean sendEvent(String deploymentId, DeploymentState currentState, Object event) {
        log.info("Sending event {} for deployment ID {} in state {}", event, deploymentId, currentState);

        StateMachine<DeploymentState, DeploymentEvent> stateMachine = build(deploymentId, currentState);

        Message<DeploymentEvent> message = MessageBuilder.withPayload((DeploymentEvent) event)
                .setHeader(DEPLOYMENT_ID_HEADER, deploymentId)
                .build();

        // Use reactive approach with Spring State Machine 3.2.x
        // Convert StateMachineEventResult to boolean
        return stateMachine.sendEvent(Mono.just(message))
                .blockLast() != null;
    }

    /**
     * Checks the status of a deployment to see if it's complete
     */
    private void checkDeploymentStatus(SmartContractDeployment deployment) {
        Map<String, Object> requestDetails = taurusProtectClient.getRequestDetails(
                deployment.getAuthToken(),
                deployment.getRequestId());

        String status = (String) requestDetails.get("status");

        if ("deployed".equalsIgnoreCase(status)) {
            // Update contract details
            if (requestDetails.containsKey("contract_address")) {
                deployment.setContractAddress((String) requestDetails.get("contract_address"));
            }

            if (requestDetails.containsKey("transaction_hash")) {
                deployment.setTransactionHash((String) requestDetails.get("transaction_hash"));
            }

            if (requestDetails.containsKey("whitelist_id")) {
                deployment.setWhitelistId((String) requestDetails.get("whitelist_id"));
            }

            deploymentRepository.save(deployment);

            // Update state to DEPLOYED
            sendEvent(deployment.getId().toString(), DeploymentState.DEPLOYMENT_APPROVED,
                    DeploymentEvent.DEPLOYMENT_COMPLETED);
        }
    }

    /**
     * Builds a response object from a deployment entity
     */
    private DeploymentResponse buildDeploymentResponse(SmartContractDeployment deployment) {
        return DeploymentResponse.builder()
                .requestId(deployment.getRequestId())
                .state(deployment.getCurrentState())
                .transactionHash(deployment.getTransactionHash())
                .contractAddress(deployment.getContractAddress())
                .whitelistId(deployment.getWhitelistId())
                .errorMessage(deployment.getErrorMessage())
                .build();
    }

    /**
     * Builds and configures a state machine for a deployment
     */
    private StateMachine<DeploymentState, DeploymentEvent> build(String deploymentId, DeploymentState currentState) {
        StateMachine<DeploymentState, DeploymentEvent> stateMachine = stateMachineFactory.getStateMachine(deploymentId);

        // Use proper reactive methods for Spring State Machine 3.2.x
        stateMachine.stopReactively().block();

        stateMachine.getStateMachineAccessor()
                .doWithAllRegions(accessor -> {
                    // Using the non-deprecated method for StateMachineContext in Spring State
                    // Machine 3.2.x
                    accessor.resetStateMachineReactively(
                            new DefaultStateMachineContext<>(currentState, null, null, null, null))
                            .block();
                });

        stateMachine.startReactively().block();

        return stateMachine;
    }
}
