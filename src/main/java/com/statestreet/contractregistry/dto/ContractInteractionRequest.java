package com.statestreet.contractregistry.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * DTO for requesting an interaction with a deployed smart contract.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContractInteractionRequest {

    @NotBlank(message = "Contract address is required")
    private String contractAddress;

    @NotBlank(message = "Function name is required")
    private String functionName;

    private String functionParams;

    @NotBlank(message = "Initiator ID is required")
    private String initiatedBy;

    private String appId;
    
    // Additional transaction-specific fields
    private String gasLimit;
    private String gasPrice;
    private String value;
    
    // Explicit getters and setters in case Lombok doesn't work
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
    
    public String getGasLimit() {
        return gasLimit;
    }
    
    public void setGasLimit(String gasLimit) {
        this.gasLimit = gasLimit;
    }
    
    public String getGasPrice() {
        return gasPrice;
    }
    
    public void setGasPrice(String gasPrice) {
        this.gasPrice = gasPrice;
    }
    
    public String getValue() {
        return value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
}
