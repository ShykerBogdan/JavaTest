package com.statestreet.contractregistry.dto;

import java.time.LocalDateTime;

import com.statestreet.contractregistry.statemachine.ContractDeployment.DeploymentState;

public class DeploymentResponse {
    
    // Static builder class
    public static Builder builder() {
        return new Builder();
    }
    
    // Builder pattern implementation
    public static class Builder {
        private final DeploymentResponse instance = new DeploymentResponse();
        
        public Builder requestId(String requestId) {
            instance.requestId = requestId;
            return this;
        }
        
        public Builder contractName(String contractName) {
            instance.contractName = contractName;
            return this;
        }
        
        public Builder status(String status) {
            instance.status = status;
            return this;
        }
        
        public Builder state(DeploymentState state) {
            instance.state = state;
            return this;
        }
        
        public Builder transactionHash(String transactionHash) {
            instance.transactionHash = transactionHash;
            return this;
        }
        
        public Builder contractAddress(String contractAddress) {
            instance.contractAddress = contractAddress;
            return this;
        }
        
        public Builder whitelistId(String whitelistId) {
            instance.whitelistId = whitelistId;
            return this;
        }
        
        public Builder errorMessage(String errorMessage) {
            instance.errorMessage = errorMessage;
            return this;
        }
        
        public Builder requestedAt(LocalDateTime requestedAt) {
            instance.requestedAt = requestedAt;
            return this;
        }
        
        public Builder approvedAt(LocalDateTime approvedAt) {
            instance.approvedAt = approvedAt;
            return this;
        }
        
        public Builder deployedAt(LocalDateTime deployedAt) {
            instance.deployedAt = deployedAt;
            return this;
        }
        
        public DeploymentResponse build() {
            return instance;
        }
    }
    
    private String requestId;
    private String contractName;
    private String status;
    private DeploymentState state;
    private String transactionHash;
    private String contractAddress;
    private String whitelistId;
    private String errorMessage;
    private LocalDateTime requestedAt;
    private LocalDateTime approvedAt;
    private LocalDateTime deployedAt;
    
    // Getters and setters
    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
    
    public String getContractName() {
        return contractName;
    }
    
    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DeploymentState getState() {
        return state;
    }

    public void setState(DeploymentState state) {
        this.state = state;
    }

    public String getTransactionHash() {
        return transactionHash;
    }

    public void setTransactionHash(String transactionHash) {
        this.transactionHash = transactionHash;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    public String getWhitelistId() {
        return whitelistId;
    }

    public void setWhitelistId(String whitelistId) {
        this.whitelistId = whitelistId;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
    
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
    public LocalDateTime getRequestedAt() {
        return requestedAt;
    }

    public void setRequestedAt(LocalDateTime requestedAt) {
        this.requestedAt = requestedAt;
    }

    public LocalDateTime getApprovedAt() {
        return approvedAt;
    }

    public void setApprovedAt(LocalDateTime approvedAt) {
        this.approvedAt = approvedAt;
    }

    public LocalDateTime getDeployedAt() {
        return deployedAt;
    }

    public void setDeployedAt(LocalDateTime deployedAt) {
        this.deployedAt = deployedAt;
    }
    
    // No-args constructor
    public DeploymentResponse() {
    }
    
    // All-args constructor
    public DeploymentResponse(String requestId, String contractName, String status, DeploymentState state, String transactionHash, 
                           String contractAddress, String whitelistId, String errorMessage, LocalDateTime requestedAt, LocalDateTime approvedAt, LocalDateTime deployedAt) {
        this.requestId = requestId;
        this.contractName = contractName;
        this.status = status;
        this.state = state;
        this.transactionHash = transactionHash;
        this.contractAddress = contractAddress;
        this.whitelistId = whitelistId;
        this.errorMessage = errorMessage;
        this.requestedAt = requestedAt;
        this.approvedAt = approvedAt;
        this.deployedAt = deployedAt;
    }
}
