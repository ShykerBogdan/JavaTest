package com.statestreet.contractregistry.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * DTO for returning results of an interaction with a deployed smart contract.
 */
@Data
public class ContractInteractionResponse {

    private String interactionId;
    private String contractAddress;
    private String functionName;
    private String functionParams;
    private String transactionHash;
    private String status;
    private String result;
    private String errorMessage;
    private Long gasUsed;
    private LocalDateTime completedAt;
    private String initiatedBy;
    private String appId;

    // Explicit constructor for the builder
    private ContractInteractionResponse(String interactionId, String contractAddress, String functionName, String functionParams, String transactionHash, String status, String result, String errorMessage, Long gasUsed, LocalDateTime completedAt, String initiatedBy, String appId) {
        this.interactionId = interactionId;
        this.contractAddress = contractAddress;
        this.functionName = functionName;
        this.functionParams = functionParams;
        this.transactionHash = transactionHash;
        this.status = status;
        this.result = result;
        this.errorMessage = errorMessage;
        this.gasUsed = gasUsed;
        this.completedAt = completedAt;
        this.initiatedBy = initiatedBy;
        this.appId = appId;
    }

    // Public no-arg constructor for frameworks like Jackson
    public ContractInteractionResponse() {}

    // Explicit getters and setters in case Lombok doesn't work
    public String getInteractionId() {
        return interactionId;
    }
    
    public void setInteractionId(String interactionId) {
        this.interactionId = interactionId;
    }
    
    public String getContractAddress() {
        return contractAddress;
    }
    
    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }
    
    public String getFunctionName() {
        return functionName;
    }
    
    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }
    
    public String getFunctionParams() {
        return functionParams;
    }
    
    public void setFunctionParams(String functionParams) {
        this.functionParams = functionParams;
    }
    
    public String getTransactionHash() {
        return transactionHash;
    }
    
    public void setTransactionHash(String transactionHash) {
        this.transactionHash = transactionHash;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getResult() {
        return result;
    }
    
    public void setResult(String result) {
        this.result = result;
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }
    
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
    public Long getGasUsed() {
        return gasUsed;
    }
    
    public void setGasUsed(Long gasUsed) {
        this.gasUsed = gasUsed;
    }
    
    public LocalDateTime getCompletedAt() {
        return completedAt;
    }
    
    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }
    
    public String getInitiatedBy() {
        return initiatedBy;
    }
    
    public void setInitiatedBy(String initiatedBy) {
        this.initiatedBy = initiatedBy;
    }
    
    public String getAppId() {
        return appId;
    }
    
    public void setAppId(String appId) {
        this.appId = appId;
    }

    // Explicit Builder Class
    public static class ContractInteractionResponseBuilder {
        private String interactionId;
        private String contractAddress;
        private String functionName;
        private String functionParams;
        private String transactionHash;
        private String status;
        private String result;
        private String errorMessage;
        private Long gasUsed;
        private LocalDateTime completedAt;
        private String initiatedBy;
        private String appId;

        ContractInteractionResponseBuilder() {}

        public ContractInteractionResponseBuilder interactionId(String interactionId) {
            this.interactionId = interactionId;
            return this;
        }

        public ContractInteractionResponseBuilder contractAddress(String contractAddress) {
            this.contractAddress = contractAddress;
            return this;
        }

        public ContractInteractionResponseBuilder functionName(String functionName) {
            this.functionName = functionName;
            return this;
        }

        public ContractInteractionResponseBuilder functionParams(String functionParams) {
            this.functionParams = functionParams;
            return this;
        }

        public ContractInteractionResponseBuilder transactionHash(String transactionHash) {
            this.transactionHash = transactionHash;
            return this;
        }

        public ContractInteractionResponseBuilder status(String status) {
            this.status = status;
            return this;
        }

        public ContractInteractionResponseBuilder result(String result) {
            this.result = result;
            return this;
        }

        public ContractInteractionResponseBuilder errorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
            return this;
        }

        public ContractInteractionResponseBuilder gasUsed(Long gasUsed) {
            this.gasUsed = gasUsed;
            return this;
        }

        public ContractInteractionResponseBuilder completedAt(LocalDateTime completedAt) {
            this.completedAt = completedAt;
            return this;
        }

        public ContractInteractionResponseBuilder initiatedBy(String initiatedBy) {
            this.initiatedBy = initiatedBy;
            return this;
        }

        public ContractInteractionResponseBuilder appId(String appId) {
            this.appId = appId;
            return this;
        }

        public ContractInteractionResponse build() {
            return new ContractInteractionResponse(interactionId, contractAddress, functionName, functionParams, transactionHash, status, result, errorMessage, gasUsed, completedAt, initiatedBy, appId);
        }

        public String toString() {
            return "ContractInteractionResponse.ContractInteractionResponseBuilder(interactionId=" + this.interactionId + ", contractAddress=" + this.contractAddress + ", functionName=" + this.functionName + ", functionParams=" + this.functionParams + ", transactionHash=" + this.transactionHash + ", status=" + this.status + ", result=" + this.result + ", errorMessage=" + this.errorMessage + ", gasUsed=" + this.gasUsed + ", completedAt=" + this.completedAt + ", initiatedBy=" + this.initiatedBy + ", appId=" + this.appId + ")";
        }
    }

    // Method to access the builder
    public static ContractInteractionResponseBuilder builder() {
        return new ContractInteractionResponseBuilder();
    }
}
