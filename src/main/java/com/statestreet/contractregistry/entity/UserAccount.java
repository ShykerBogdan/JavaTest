package com.statestreet.contractregistry.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity representing a user account in the system.
 */
@Entity
@Table(name = "user_account")
@Data
public class UserAccount {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "user_id", nullable = false, unique = true)
    private String userId;
    
    @Column(nullable = false)
    private String username;
    
    @Column(nullable = false)
    private String email;
    
    @Column(name = "department")
    private String department;
    
    @Column(name = "role")
    private String role;
    
    @Column(name = "wallet_address")
    private String walletAddress;
    
    @Column(name = "api_key")
    private String apiKey;
    
    @Column
    private boolean active;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Column(name = "last_login")
    private LocalDateTime lastLogin;
    
    // Explicit constructor for the builder
    private UserAccount(Long id, String userId, String username, String email, String department, String role, String walletAddress, String apiKey, boolean active, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime lastLogin) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.department = department;
        this.role = role;
        this.walletAddress = walletAddress;
        this.apiKey = apiKey;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.lastLogin = lastLogin;
    }

    // Public no-arg constructor if needed elsewhere (e.g., JPA)
    public UserAccount() {}

    // Explicit Builder Class
    public static class UserAccountBuilder {
        private Long id;
        private String userId;
        private String username;
        private String email;
        private String department;
        private String role;
        private String walletAddress;
        private String apiKey;
        private boolean active;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private LocalDateTime lastLogin;

        UserAccountBuilder() {}

        public UserAccountBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public UserAccountBuilder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public UserAccountBuilder username(String username) {
            this.username = username;
            return this;
        }

        public UserAccountBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserAccountBuilder department(String department) {
            this.department = department;
            return this;
        }

        public UserAccountBuilder role(String role) {
            this.role = role;
            return this;
        }

        public UserAccountBuilder walletAddress(String walletAddress) {
            this.walletAddress = walletAddress;
            return this;
        }

        public UserAccountBuilder apiKey(String apiKey) {
            this.apiKey = apiKey;
            return this;
        }

        public UserAccountBuilder active(boolean active) {
            this.active = active;
            return this;
        }

        public UserAccountBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public UserAccountBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public UserAccountBuilder lastLogin(LocalDateTime lastLogin) {
            this.lastLogin = lastLogin;
            return this;
        }

        public UserAccount build() {
            return new UserAccount(id, userId, username, email, department, role, walletAddress, apiKey, active, createdAt, updatedAt, lastLogin);
        }

        public String toString() {
            return "UserAccount.UserAccountBuilder(id=" + this.id + ", userId=" + this.userId + ", username=" + this.username + ", email=" + this.email + ", department=" + this.department + ", role=" + this.role + ", walletAddress=" + this.walletAddress + ", apiKey=" + this.apiKey + ", active=" + this.active + ", createdAt=" + this.createdAt + ", updatedAt=" + this.updatedAt + ", lastLogin=" + this.lastLogin + ")";
        }
    }

    // Method to access the builder
    public static UserAccountBuilder builder() {
        return new UserAccountBuilder();
    }

    // Explicit Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getWalletAddress() {
        return walletAddress;
    }

    public void setWalletAddress(String walletAddress) {
        this.walletAddress = walletAddress;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
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
