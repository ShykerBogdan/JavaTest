package com.statestreet.contractregistry.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity representing a contract deployment request and its status.
 */
@Entity
@Table(name = "contract_deployment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContractDeployment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "request_id", nullable = false, unique = true)
    private String requestId;
    
    @Column(name = "contract_name", nullable = false)
    private String contractName;
    
    @Column(nullable = false)
    private String network;
    
    @Column(name = "deployment_params", columnDefinition = "TEXT")
    private String deploymentParams;
    
    @Column(nullable = false)
    private String status;
    
    @Column(name = "contract_address")
    private String contractAddress;
    
    @Column(name = "transaction_hash")
    private String transactionHash;
    
    @Column(name = "requester_id", nullable = false)
    private String requesterId;
    
    @Column(name = "approver_id")
    private String approverId;
    
    @Column(name = "requested_at")
    private LocalDateTime requestedAt;
    
    @Column(name = "approved_at")
    private LocalDateTime approvedAt;
    
    @Column(name = "deployed_at")
    private LocalDateTime deployedAt;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Explicit getters and setters in case Lombok doesn't work
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
    
    public String getContractName() {
        return contractName;
    }
    
    public void setContractName(String contractName) {
        this.contractName = contractName;
    }
    
    public String getNetwork() {
        return network;
    }
    
    public void setNetwork(String network) {
        this.network = network;
    }
    
    public String getDeploymentParams() {
        return deploymentParams;
    }
    
    public void setDeploymentParams(String deploymentParams) {
        this.deploymentParams = deploymentParams;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getContractAddress() {
        return contractAddress;
    }
    
    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }
    
    public String getTransactionHash() {
        return transactionHash;
    }
    
    public void setTransactionHash(String transactionHash) {
        this.transactionHash = transactionHash;
    }
    
    public String getRequesterId() {
        return requesterId;
    }
    
    public void setRequesterId(String requesterId) {
        this.requesterId = requesterId;
    }
    
    public String getApproverId() {
        return approverId;
    }
    
    public void setApproverId(String approverId) {
        this.approverId = approverId;
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
    
    // Explicitly define the builder method
    public static ContractDeploymentBuilder builder() {
        return new ContractDeploymentBuilder();
    }
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        requestedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Builder implementation for ContractDeployment
    public static class ContractDeploymentBuilder {
        private final ContractDeployment instance = new ContractDeployment();

        public ContractDeploymentBuilder id(Long id) {
            instance.setId(id);
            return this;
        }

        public ContractDeploymentBuilder requestId(String requestId) {
            instance.setRequestId(requestId);
            return this;
        }

        public ContractDeploymentBuilder contractName(String contractName) {
            instance.setContractName(contractName);
            return this;
        }

        public ContractDeploymentBuilder network(String network) {
            instance.setNetwork(network);
            return this;
        }

        public ContractDeploymentBuilder deploymentParams(String deploymentParams) {
            instance.setDeploymentParams(deploymentParams);
            return this;
        }

        public ContractDeploymentBuilder status(String status) {
            instance.setStatus(status);
            return this;
        }

        public ContractDeploymentBuilder contractAddress(String contractAddress) {
            instance.setContractAddress(contractAddress);
            return this;
        }

        public ContractDeploymentBuilder transactionHash(String transactionHash) {
            instance.setTransactionHash(transactionHash);
            return this;
        }

        public ContractDeploymentBuilder requesterId(String requesterId) {
            instance.setRequesterId(requesterId);
            return this;
        }

        public ContractDeploymentBuilder approverId(String approverId) {
            instance.setApproverId(approverId);
            return this;
        }

        public ContractDeploymentBuilder requestedAt(LocalDateTime requestedAt) {
            instance.setRequestedAt(requestedAt);
            return this;
        }

        public ContractDeploymentBuilder approvedAt(LocalDateTime approvedAt) {
            instance.setApprovedAt(approvedAt);
            return this;
        }

        public ContractDeploymentBuilder deployedAt(LocalDateTime deployedAt) {
            instance.setDeployedAt(deployedAt);
            return this;
        }

        public ContractDeploymentBuilder createdAt(LocalDateTime createdAt) {
            instance.setCreatedAt(createdAt);
            return this;
        }

        public ContractDeploymentBuilder updatedAt(LocalDateTime updatedAt) {
            instance.setUpdatedAt(updatedAt);
            return this;
        }

        public ContractDeployment build() {
            return instance;
        }
    }
}
