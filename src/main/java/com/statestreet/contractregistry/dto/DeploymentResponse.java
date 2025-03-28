package com.statestreet.contractregistry.dto;

import com.statestreet.contractregistry.statemachine.DeploymentState;



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
        
        public DeploymentResponse build() {
            return instance;
        }
    }
    
    private String requestId;
    private DeploymentState state;
    private String transactionHash;
    private String contractAddress;
    private String whitelistId;
    private String errorMessage;
    // Getters and setters
    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
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
    
    // No-args constructor
    public DeploymentResponse() {
    }
    
    // All-args constructor
    public DeploymentResponse(String requestId, DeploymentState state, String transactionHash, 
                           String contractAddress, String whitelistId, String errorMessage) {
        this.requestId = requestId;
        this.state = state;
        this.transactionHash = transactionHash;
        this.contractAddress = contractAddress;
        this.whitelistId = whitelistId;
        this.errorMessage = errorMessage;
    }
}
