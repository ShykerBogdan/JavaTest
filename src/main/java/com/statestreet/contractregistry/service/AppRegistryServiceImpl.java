package com.statestreet.contractregistry.service;

import com.statestreet.contractregistry.entity.AppRegistry;
import com.statestreet.contractregistry.exception.ResourceNotFoundException;
import com.statestreet.contractregistry.repository.AppRegistryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementation of the AppRegistryService interface.
 * Handles operations related to application registry management.
 */
@Service
public class AppRegistryServiceImpl implements AppRegistryService {

    private static final Logger log = LoggerFactory.getLogger(AppRegistryServiceImpl.class);
    
    private final AppRegistryRepository appRegistryRepository;
    
    /**
     * Constructor for dependency injection
     */
    public AppRegistryServiceImpl(AppRegistryRepository appRegistryRepository) {
        this.appRegistryRepository = appRegistryRepository;
    }

    @Override
    @Transactional
    public AppRegistry registerApp(AppRegistry appRegistry) {
        log.info("Registering new application: {}", appRegistry.getAppName());
        
        // Generate a unique app ID if not provided
        if (appRegistry.getAppId() == null || appRegistry.getAppId().trim().isEmpty()) {
            appRegistry.setAppId(generateAppId());
        } else if (appRegistryRepository.existsByAppId(appRegistry.getAppId())) {
            log.error("Application with ID {} already exists", appRegistry.getAppId());
            throw new IllegalArgumentException("Application with ID " + appRegistry.getAppId() + " already exists");
        }
        
        // Check if app name already exists
        if (appRegistryRepository.findByAppName(appRegistry.getAppName()).isPresent()) {
            log.error("Application with name {} already exists", appRegistry.getAppName());
            throw new IllegalArgumentException("Application with name " + appRegistry.getAppName() + " already exists");
        }
        
        // Generate an API key if not provided
        if (appRegistry.getApiKey() == null || appRegistry.getApiKey().trim().isEmpty()) {
            appRegistry.setApiKey(generateApiKeyValue());
        }
        
        // Set active status
        appRegistry.setActive(true);
        
        AppRegistry savedApp = appRegistryRepository.save(appRegistry);
        log.info("Application registered with ID: {}", savedApp.getAppId());
        
        return savedApp;
    }

    @Override
    @Transactional
    public AppRegistry updateApp(Long id, AppRegistry appRegistry) {
        log.info("Updating application with ID: {}", id);
        
        // Check if app exists
        AppRegistry existingApp = appRegistryRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Application not found with ID: {}", id);
                    return new ResourceNotFoundException("Application not found with ID: " + id);
                });
        
        // Check if trying to update to a name that already exists (and is not this app)
        if (!existingApp.getAppName().equals(appRegistry.getAppName()) && 
                appRegistryRepository.findByAppName(appRegistry.getAppName()).isPresent()) {
            log.error("Cannot update application, name {} is already in use", appRegistry.getAppName());
            throw new IllegalArgumentException("Application name already in use: " + appRegistry.getAppName());
        }
        
        // Update fields
        existingApp.setAppName(appRegistry.getAppName());
        existingApp.setOwnerDepartment(appRegistry.getOwnerDepartment());
        existingApp.setContactEmail(appRegistry.getContactEmail());
        existingApp.setDescription(appRegistry.getDescription());
        
        AppRegistry updatedApp = appRegistryRepository.save(existingApp);
        log.info("Application updated: {}", updatedApp.getAppName());
        
        return updatedApp;
    }

    @Override
    public Optional<AppRegistry> getAppById(Long id) {
        log.info("Getting application by ID: {}", id);
        return appRegistryRepository.findById(id);
    }

    @Override
    public Optional<AppRegistry> getAppByAppId(String appId) {
        log.info("Getting application by App ID: {}", appId);
        return appRegistryRepository.findByAppId(appId);
    }

    @Override
    public Optional<AppRegistry> getAppByName(String appName) {
        log.info("Getting application by name: {}", appName);
        return appRegistryRepository.findByAppName(appName);
    }

    @Override
    public List<AppRegistry> getAllApps() {
        log.info("Getting all registered applications");
        return appRegistryRepository.findAll();
    }

    @Override
    public List<AppRegistry> getAppsByDepartment(String department) {
        log.info("Getting applications for department: {}", department);
        return appRegistryRepository.findByOwnerDepartment(department);
    }

    @Override
    @Transactional
    public AppRegistry setAppStatus(String appId, boolean active) {
        log.info("Setting application {} status to: {}", appId, active);
        
        AppRegistry app = appRegistryRepository.findByAppId(appId)
                .orElseThrow(() -> {
                    log.error("Application not found with App ID: {}", appId);
                    return new ResourceNotFoundException("Application not found with App ID: " + appId);
                });
        
        app.setActive(active);
        AppRegistry updatedApp = appRegistryRepository.save(app);
        
        log.info("Application status updated for {}: active={}", appId, active);
        return updatedApp;
    }

    @Override
    @Transactional
    public AppRegistry generateApiKey(String appId) {
        log.info("Generating new API key for application: {}", appId);
        
        AppRegistry app = appRegistryRepository.findByAppId(appId)
                .orElseThrow(() -> {
                    log.error("Application not found with App ID: {}", appId);
                    return new ResourceNotFoundException("Application not found with App ID: " + appId);
                });
        
        String apiKey = generateApiKeyValue();
        app.setApiKey(apiKey);
        
        AppRegistry updatedApp = appRegistryRepository.save(app);
        log.info("API key generated for application: {}", appId);
        
        return updatedApp;
    }

    @Override
    public boolean validateApiKey(String apiKey) {
        log.info("Validating API key");
        
        Optional<AppRegistry> app = appRegistryRepository.findByApiKey(apiKey);
        boolean isValid = app.isPresent() && app.get().isActive();
        
        if (isValid) {
            log.info("API key validated successfully for application: {}", app.get().getAppId());
        } else {
            log.warn("Invalid or inactive API key");
        }
        
        return isValid;
    }
    
    /**
     * Helper method to generate a unique app ID
     */
    private String generateAppId() {
        return "APP-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
    
    /**
     * Helper method to generate an API key
     */
    private String generateApiKeyValue() {
        return UUID.randomUUID().toString();
    }
}
