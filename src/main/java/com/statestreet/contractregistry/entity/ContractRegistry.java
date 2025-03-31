package com.statestreet.contractregistry.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity representing a deployed smart contract in the registry.
 */
@Entity
@Table(name = "contract_registry")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContractRegistry {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String contractAddress;
    
    @Column(nullable = false)
    private String contractName;
    
    @Column(nullable = false)
    private String version;
    
    @Column(nullable = false)
    private String network;
    
    @Column(nullable = false)
    private String owner;
    
    @Column(nullable = false)
    private String abi;
    
    @Column(name = "deployment_timestamp", nullable = false)
    private LocalDateTime deploymentTimestamp;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Column
    private boolean whitelisted;
    
    @Column(name = "whitelist_timestamp")
    private LocalDateTime whitelistTimestamp;
    
    // Explicit getters and setters in case Lombok doesn't work
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getContractAddress() {
        return contractAddress;
    }
    
    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }
    
    public String getContractName() {
        return contractName;
    }
    
    public void setContractName(String contractName) {
        this.contractName = contractName;
    }
    
    public String getVersion() {
        return version;
    }
    
    public void setVersion(String version) {
        this.version = version;
    }
    
    public String getNetwork() {
        return network;
    }
    
    public void setNetwork(String network) {
        this.network = network;
    }
    
    public String getOwner() {
        return owner;
    }
    
    public void setOwner(String owner) {
        this.owner = owner;
    }
    
    public String getAbi() {
        return abi;
    }
    
    public void setAbi(String abi) {
        this.abi = abi;
    }
    
    public LocalDateTime getDeploymentTimestamp() {
        return deploymentTimestamp;
    }
    
    public void setDeploymentTimestamp(LocalDateTime deploymentTimestamp) {
        this.deploymentTimestamp = deploymentTimestamp;
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
    
    public boolean isWhitelisted() {
        return whitelisted;
    }
    
    public void setWhitelisted(boolean whitelisted) {
        this.whitelisted = whitelisted;
    }
    
    public LocalDateTime getWhitelistTimestamp() {
        return whitelistTimestamp;
    }
    
    public void setWhitelistTimestamp(LocalDateTime whitelistTimestamp) {
        this.whitelistTimestamp = whitelistTimestamp;
    }
    
    // Explicitly define the builder method
    public static ContractRegistryBuilder builder() {
        return new ContractRegistryBuilder();
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
    
    // Builder implementation for ContractRegistry
    public static class ContractRegistryBuilder {
        private final ContractRegistry instance = new ContractRegistry();
        
        public ContractRegistryBuilder id(Long id) {
            instance.id = id;
            return this;
        }
        
        public ContractRegistryBuilder contractAddress(String contractAddress) {
            instance.contractAddress = contractAddress;
            return this;
        }
        
        public ContractRegistryBuilder contractName(String contractName) {
            instance.contractName = contractName;
            return this;
        }
        
        public ContractRegistryBuilder version(String version) {
            instance.version = version;
            return this;
        }
        
        public ContractRegistryBuilder network(String network) {
            instance.network = network;
            return this;
        }
        
        public ContractRegistryBuilder owner(String owner) {
            instance.owner = owner;
            return this;
        }
        
        public ContractRegistryBuilder abi(String abi) {
            instance.abi = abi;
            return this;
        }
        
        public ContractRegistryBuilder deploymentTimestamp(LocalDateTime deploymentTimestamp) {
            instance.deploymentTimestamp = deploymentTimestamp;
            return this;
        }
        
        public ContractRegistryBuilder createdAt(LocalDateTime createdAt) {
            instance.createdAt = createdAt;
            return this;
        }
        
        public ContractRegistryBuilder updatedAt(LocalDateTime updatedAt) {
            instance.updatedAt = updatedAt;
            return this;
        }
        
        public ContractRegistryBuilder whitelisted(boolean whitelisted) {
            instance.whitelisted = whitelisted;
            return this;
        }
        
        public ContractRegistryBuilder whitelistTimestamp(LocalDateTime whitelistTimestamp) {
            instance.whitelistTimestamp = whitelistTimestamp;
            return this;
        }
        
        public ContractRegistry build() {
            return instance;
        }
    }
}
