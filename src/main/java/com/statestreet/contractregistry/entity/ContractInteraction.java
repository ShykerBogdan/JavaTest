package com.statestreet.contractregistry.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity representing an interaction with a deployed smart contract.
 */
@Entity
@Table(name = "contract_interaction")
@Data
public class ContractInteraction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "interaction_id", nullable = false, unique = true)
    private String interactionId;
    
    @Column(name = "contract_address", nullable = false)
    private String contractAddress;
    
    @Column(name = "function_name", nullable = false)
    private String functionName;
    
    @Column(name = "function_params", columnDefinition = "TEXT")
    private String functionParams;
    
    @Column(name = "transaction_hash")
    private String transactionHash;
    
    @Column(nullable = false)
    private String status;
    
    @Column(name = "initiated_by", nullable = false)
    private String initiatedBy;
    
    @Column(name = "app_id")
    private String appId;
    
    @Column(name = "result", columnDefinition = "TEXT")
    private String result;
    
    @Column(name = "error_message", columnDefinition = "TEXT")
    private String errorMessage;
    
    @Column(name = "gas_used")
    private Long gasUsed;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    // Explicit constructor for the builder
    private ContractInteraction(Long id, String interactionId, String contractAddress, String functionName, String functionParams, String transactionHash, String status, String initiatedBy, String appId, String result, String errorMessage, Long gasUsed, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime completedAt) {
        this.id = id;
        this.interactionId = interactionId;
        this.contractAddress = contractAddress;
        this.functionName = functionName;
        this.functionParams = functionParams;
        this.transactionHash = transactionHash;
        this.status = status;
        this.initiatedBy = initiatedBy;
        this.appId = appId;
        this.result = result;
        this.errorMessage = errorMessage;
        this.gasUsed = gasUsed;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.completedAt = completedAt;
    }

    // Public no-arg constructor for JPA
    public ContractInteraction() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public static class ContractInteractionBuilder {
        private Long id;
        private String interactionId;
        private String contractAddress;
        private String functionName;
        private String functionParams;
        private String transactionHash;
        private String status;
        private String initiatedBy;
        private String appId;
        private String result;
        private String errorMessage;
        private Long gasUsed;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private LocalDateTime completedAt;

        ContractInteractionBuilder() {}

        public ContractInteractionBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ContractInteractionBuilder interactionId(String interactionId) {
            this.interactionId = interactionId;
            return this;
        }

        public ContractInteractionBuilder contractAddress(String contractAddress) {
            this.contractAddress = contractAddress;
            return this;
        }

        public ContractInteractionBuilder functionName(String functionName) {
            this.functionName = functionName;
            return this;
        }

        public ContractInteractionBuilder functionParams(String functionParams) {
            this.functionParams = functionParams;
            return this;
        }

        public ContractInteractionBuilder transactionHash(String transactionHash) {
            this.transactionHash = transactionHash;
            return this;
        }

        public ContractInteractionBuilder status(String status) {
            this.status = status;
            return this;
        }

        public ContractInteractionBuilder initiatedBy(String initiatedBy) {
            this.initiatedBy = initiatedBy;
            return this;
        }

        public ContractInteractionBuilder appId(String appId) {
            this.appId = appId;
            return this;
        }

        public ContractInteractionBuilder result(String result) {
            this.result = result;
            return this;
        }

        public ContractInteractionBuilder errorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
            return this;
        }

        public ContractInteractionBuilder gasUsed(Long gasUsed) {
            this.gasUsed = gasUsed;
            return this;
        }

        public ContractInteractionBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public ContractInteractionBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public ContractInteractionBuilder completedAt(LocalDateTime completedAt) {
            this.completedAt = completedAt;
            return this;
        }

        public ContractInteraction build() {
            return new ContractInteraction(id, interactionId, contractAddress, functionName, functionParams, transactionHash, status, initiatedBy, appId, result, errorMessage, gasUsed, createdAt, updatedAt, completedAt);
        }

        public String toString() {
            return "ContractInteraction.ContractInteractionBuilder(id=" + this.id + ", interactionId=" + this.interactionId + ", contractAddress=" + this.contractAddress + ", functionName=" + this.functionName + ", functionParams=" + this.functionParams + ", transactionHash=" + this.transactionHash + ", status=" + this.status + ", initiatedBy=" + this.initiatedBy + ", appId=" + this.appId + ", result=" + this.result + ", errorMessage=" + this.errorMessage + ", gasUsed=" + this.gasUsed + ", createdAt=" + this.createdAt + ", updatedAt=" + this.updatedAt + ", completedAt=" + this.completedAt + ")";
        }
    }

    public static ContractInteractionBuilder builder() {
        return new ContractInteractionBuilder();
    }
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
