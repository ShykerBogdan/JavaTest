package com.statestreet.contractregistry.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * DTO for returning user account information.
 */
@Data
public class UserAccountResponse {

    private String userId;
    private String username;
    private String email;
    private String department;
    private String role;
    private String walletAddress;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;
    
    // API key is not included in response for security

    // Explicit constructor for the builder
    private UserAccountResponse(String userId, String username, String email, String department, String role, String walletAddress, boolean active, LocalDateTime createdAt, LocalDateTime lastLogin) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.department = department;
        this.role = role;
        this.walletAddress = walletAddress;
        this.active = active;
        this.createdAt = createdAt;
        this.lastLogin = lastLogin;
    }

    // Public no-arg constructor (might be needed for frameworks like Jackson)
    public UserAccountResponse() {}

    // Explicit Getters
    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getDepartment() {
        return department;
    }

    public String getRole() {
        return role;
    }

    public String getWalletAddress() {
        return walletAddress;
    }

    public boolean isActive() {
        return active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    // Explicit Setters
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setWalletAddress(String walletAddress) {
        this.walletAddress = walletAddress;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    // Explicit Builder Class
    public static class UserAccountResponseBuilder {
        private String userId;
        private String username;
        private String email;
        private String department;
        private String role;
        private String walletAddress;
        private boolean active;
        private LocalDateTime createdAt;
        private LocalDateTime lastLogin;

        UserAccountResponseBuilder() {}

        public UserAccountResponseBuilder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public UserAccountResponseBuilder username(String username) {
            this.username = username;
            return this;
        }

        public UserAccountResponseBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserAccountResponseBuilder department(String department) {
            this.department = department;
            return this;
        }

        public UserAccountResponseBuilder role(String role) {
            this.role = role;
            return this;
        }

        public UserAccountResponseBuilder walletAddress(String walletAddress) {
            this.walletAddress = walletAddress;
            return this;
        }

        public UserAccountResponseBuilder active(boolean active) {
            this.active = active;
            return this;
        }

        public UserAccountResponseBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public UserAccountResponseBuilder lastLogin(LocalDateTime lastLogin) {
            this.lastLogin = lastLogin;
            return this;
        }

        public UserAccountResponse build() {
            return new UserAccountResponse(userId, username, email, department, role, walletAddress, active, createdAt, lastLogin);
        }

        public String toString() {
            return "UserAccountResponse.UserAccountResponseBuilder(userId=" + this.userId + ", username=" + this.username + ", email=" + this.email + ", department=" + this.department + ", role=" + this.role + ", walletAddress=" + this.walletAddress + ", active=" + this.active + ", createdAt=" + this.createdAt + ", lastLogin=" + this.lastLogin + ")";
        }
    }

    // Method to access the builder
    public static UserAccountResponseBuilder builder() {
        return new UserAccountResponseBuilder();
    }
}
