package com.statestreet.contractregistry.exception;

/**
 * Exception thrown when there's an error in the smart contract deployment process
 */
public class DeploymentException extends RuntimeException {
    
    public DeploymentException(String message) {
        super(message);
    }
    
    public DeploymentException(String message, Throwable cause) {
        super(message, cause);
    }
}
