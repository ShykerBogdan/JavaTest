package com.statestreet.contractregistry.exception;

import com.fasterxml.jackson.annotation.JsonInclude;


import java.time.LocalDateTime;
import java.util.Map;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    
    // Static builder class
    public static Builder builder() {
        return new Builder();
    }
    
    // Builder pattern implementation
    public static class Builder {
        private final ErrorResponse instance = new ErrorResponse();
        
        public Builder timestamp(LocalDateTime timestamp) {
            instance.timestamp = timestamp;
            return this;
        }
        
        public Builder status(int status) {
            instance.status = status;
            return this;
        }
        
        public Builder error(String error) {
            instance.error = error;
            return this;
        }
        
        public Builder message(String message) {
            instance.message = message;
            return this;
        }
        
        public Builder details(Map<String, String> details) {
            instance.details = details;
            return this;
        }
        
        public ErrorResponse build() {
            return instance;
        }
    }
    
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private Map<String, String> details;
    // Getters and setters
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, String> getDetails() {
        return details;
    }

    public void setDetails(Map<String, String> details) {
        this.details = details;
    }
    
    // No-args constructor
    public ErrorResponse() {
    }
    
    // All-args constructor
    public ErrorResponse(LocalDateTime timestamp, int status, String error, String message, Map<String, String> details) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.details = details;
    }
}
