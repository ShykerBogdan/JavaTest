package com.statestreet.contractregistry.model;

import javax.persistence.*;

import com.statestreet.contractregistry.statemachine.ContractDeployment.DeploymentState;

import java.time.LocalDateTime;

@Entity
@Table(name = "smart_contract_deployments")

public class SmartContractDeployment {
    
    // Static builder class
    public static Builder builder() {
        return new Builder();
    }
    
    // Builder pattern implementation
    public static class Builder {
        private final SmartContractDeployment instance = new SmartContractDeployment();
        
        // Constructor is needed here because the Builder creates an instance
        
        public Builder requestId(String requestId) {
            instance.requestId = requestId;
            return this;
        }
        
        public Builder authToken(String authToken) {
            instance.authToken = authToken;
            return this;
        }
        
        public Builder contractBytecode(String contractBytecode) {
            instance.contractBytecode = contractBytecode;
            return this;
        }
        
        public Builder contractName(String contractName) {
            instance.contractName = contractName;
            return this;
        }
        
        public Builder constructorArgs(String constructorArgs) {
            instance.constructorArgs = constructorArgs;
            return this;
        }
        
        public Builder hashValue(String hashValue) {
            instance.hashValue = hashValue;
            return this;
        }
        
        public Builder signedHash(String signedHash) {
            instance.signedHash = signedHash;
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
        
        public Builder whitelistHash(String whitelistHash) {
            instance.whitelistHash = whitelistHash;
            return this;
        }
        
        public Builder signedWhitelistHash(String signedWhitelistHash) {
            instance.signedWhitelistHash = signedWhitelistHash;
            return this;
        }
        
        public Builder currentState(DeploymentState currentState) {
            instance.currentState = currentState;
            return this;
        }
        
        public Builder errorMessage(String errorMessage) {
            instance.errorMessage = errorMessage;
            return this;
        }
        
        public Builder createdAt(LocalDateTime createdAt) {
            instance.createdAt = createdAt;
            return this;
        }
        
        public Builder updatedAt(LocalDateTime updatedAt) {
            instance.updatedAt = updatedAt;
            return this;
        }
        
        public SmartContractDeployment build() {
            return instance;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "request_id")
    private String requestId;

    @Column(name = "auth_token")
    private String authToken;

    @Column(name = "contract_bytecode", length = 50000)
    private String contractBytecode;

    @Column(name = "contract_name")
    private String contractName;

    @Column(name = "constructor_args", length = 10000)
    private String constructorArgs;

    @Column(name = "hash_value", length = 1000)
    private String hashValue;

    @Column(name = "signed_hash", length = 1000)
    private String signedHash;

    @Column(name = "transaction_hash")
    private String transactionHash;

    @Column(name = "contract_address")
    private String contractAddress;

    @Column(name = "whitelist_id")
    private String whitelistId;

    @Column(name = "whitelist_hash", length = 1000)
    private String whitelistHash;

    @Column(name = "signed_whitelist_hash", length = 1000)
    private String signedWhitelistHash;

    @Enumerated(EnumType.STRING)
    @Column(name = "current_state")
    private DeploymentState currentState;

    @Column(name = "error_message", length = 2000)
    private String errorMessage;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getContractBytecode() {
        return contractBytecode;
    }

    public void setContractBytecode(String contractBytecode) {
        this.contractBytecode = contractBytecode;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getConstructorArgs() {
        return constructorArgs;
    }

    public void setConstructorArgs(String constructorArgs) {
        this.constructorArgs = constructorArgs;
    }

    public String getHashValue() {
        return hashValue;
    }

    public void setHashValue(String hashValue) {
        this.hashValue = hashValue;
    }

    public String getSignedHash() {
        return signedHash;
    }

    public void setSignedHash(String signedHash) {
        this.signedHash = signedHash;
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

    public String getWhitelistHash() {
        return whitelistHash;
    }

    public void setWhitelistHash(String whitelistHash) {
        this.whitelistHash = whitelistHash;
    }

    public String getSignedWhitelistHash() {
        return signedWhitelistHash;
    }

    public void setSignedWhitelistHash(String signedWhitelistHash) {
        this.signedWhitelistHash = signedWhitelistHash;
    }

    public DeploymentState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(DeploymentState currentState) {
        this.currentState = currentState;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
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
    
    // No-args constructor
    public SmartContractDeployment() {}
    
    // All-args constructor
    public SmartContractDeployment(Long id, String requestId, String authToken, String contractBytecode, String contractName,
                                 String constructorArgs, String hashValue, String signedHash, String transactionHash,
                                 String contractAddress, String whitelistId, String whitelistHash, String signedWhitelistHash,
                                 DeploymentState currentState, String errorMessage, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.requestId = requestId;
        this.authToken = authToken;
        this.contractBytecode = contractBytecode;
        this.contractName = contractName;
        this.constructorArgs = constructorArgs;
        this.hashValue = hashValue;
        this.signedHash = signedHash;
        this.transactionHash = transactionHash;
        this.contractAddress = contractAddress;
        this.whitelistId = whitelistId;
        this.whitelistHash = whitelistHash;
        this.signedWhitelistHash = signedWhitelistHash;
        this.currentState = currentState;
        this.errorMessage = errorMessage;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
