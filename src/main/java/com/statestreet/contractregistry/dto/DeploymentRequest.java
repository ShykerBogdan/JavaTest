package com.statestreet.contractregistry.dto;

import javax.validation.constraints.NotBlank;

public class DeploymentRequest {
    
    // Static builder class
    public static Builder builder() {
        return new Builder();
    }
    
    // Builder pattern implementation
    public static class Builder {
        private final DeploymentRequest instance = new DeploymentRequest();
        
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
        
        public Builder network(String network) {
            instance.network = network;
            return this;
        }
        
        public Builder deploymentParams(String deploymentParams) {
            instance.deploymentParams = deploymentParams;
            return this;
        }
        
        public Builder requesterId(String requesterId) {
            instance.requesterId = requesterId;
            return this;
        }
        
        public DeploymentRequest build() {
            return instance;
        }
    }
    
    @NotBlank(message = "Contract bytecode is required")
    private String contractBytecode;
    
    @NotBlank(message = "Contract name is required")
    private String contractName;
    
    private String constructorArgs;
    
    @NotBlank(message = "Network is required")
    private String network;
    
    private String deploymentParams;
    
    @NotBlank(message = "Requester ID is required")
    private String requesterId;
    
    // Getters and setters
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
    
    public String getRequesterId() {
        return requesterId;
    }
    
    public void setRequesterId(String requesterId) {
        this.requesterId = requesterId;
    }
    
    // No-args constructor
    public DeploymentRequest() {
    }
    
    // All-args constructor
    public DeploymentRequest(String contractBytecode, String contractName, String constructorArgs, 
                            String network, String deploymentParams, String requesterId) {
        this.contractBytecode = contractBytecode;
        this.contractName = contractName;
        this.constructorArgs = constructorArgs;
        this.network = network;
        this.deploymentParams = deploymentParams;
        this.requesterId = requesterId;
    }
}
