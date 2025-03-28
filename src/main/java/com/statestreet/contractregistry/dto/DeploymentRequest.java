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
        
        public DeploymentRequest build() {
            return instance;
        }
    }
    
    @NotBlank(message = "Contract bytecode is required")
    private String contractBytecode;
    
    @NotBlank(message = "Contract name is required")
    private String contractName;
    
    private String constructorArgs;
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
    
    // No-args constructor
    public DeploymentRequest() {
    }
    
    // All-args constructor
    public DeploymentRequest(String contractBytecode, String contractName, String constructorArgs) {
        this.contractBytecode = contractBytecode;
        this.contractName = contractName;
        this.constructorArgs = constructorArgs;
    }
}
