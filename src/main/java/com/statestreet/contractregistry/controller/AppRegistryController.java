package com.statestreet.contractregistry.controller;

import com.statestreet.contractregistry.entity.AppRegistry;
import com.statestreet.contractregistry.service.AppRegistryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * REST controller for application registry operations.
 * Handles API endpoints related to managing registered applications.
 */
@RestController
@RequestMapping("/api/apps")
public class AppRegistryController {

    private static final Logger log = LoggerFactory.getLogger(AppRegistryController.class);

    private final AppRegistryService appRegistryService;

    /**
     * Constructor for dependency injection
     */
    public AppRegistryController(AppRegistryService appRegistryService) {
        this.appRegistryService = appRegistryService;
    }

    /**
     * Endpoint to register a new application
     * 
     * @param appRegistry The application to register
     * @return The registered application with generated ID
     */
    @PostMapping
    public ResponseEntity<AppRegistry> registerApp(@Valid @RequestBody AppRegistry appRegistry) {
        log.info("Received request to register application: {}", appRegistry.getAppName());
        AppRegistry savedApp = appRegistryService.registerApp(appRegistry);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedApp);
    }

    /**
     * Endpoint to update an existing registered application
     * 
     * @param id The ID of the application to update
     * @param appRegistry The updated application data
     * @return The updated application
     */
    @PutMapping("/{id}")
    public ResponseEntity<AppRegistry> updateApp(
            @PathVariable Long id, 
            @Valid @RequestBody AppRegistry appRegistry) {
        log.info("Received request to update application with ID: {}", id);
        AppRegistry updatedApp = appRegistryService.updateApp(id, appRegistry);
        return ResponseEntity.ok(updatedApp);
    }

    /**
     * Endpoint to get an application by its ID
     * 
     * @param id The ID of the application to retrieve
     * @return The application if found
     */
    @GetMapping("/{id}")
    public ResponseEntity<AppRegistry> getAppById(@PathVariable Long id) {
        log.info("Received request to get application with ID: {}", id);
        return appRegistryService.getAppById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Endpoint to get an application by its unique app ID
     * 
     * @param appId The unique identifier of the application
     * @return The application if found
     */
    @GetMapping("/appId/{appId}")
    public ResponseEntity<AppRegistry> getAppByAppId(@PathVariable String appId) {
        log.info("Received request to get application with App ID: {}", appId);
        return appRegistryService.getAppByAppId(appId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Endpoint to get an application by its name
     * 
     * @param appName The name of the application
     * @return The application if found
     */
    @GetMapping("/name/{appName}")
    public ResponseEntity<AppRegistry> getAppByName(@PathVariable String appName) {
        log.info("Received request to get application with name: {}", appName);
        return appRegistryService.getAppByName(appName)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Endpoint to get all registered applications
     * 
     * @return List of all applications
     */
    @GetMapping
    public ResponseEntity<List<AppRegistry>> getAllApps() {
        log.info("Received request to get all registered applications");
        List<AppRegistry> apps = appRegistryService.getAllApps();
        return ResponseEntity.ok(apps);
    }

    /**
     * Endpoint to get all applications owned by a specific department
     * 
     * @param department The department that owns the applications
     * @return List of applications owned by the department
     */
    @GetMapping("/department/{department}")
    public ResponseEntity<List<AppRegistry>> getAppsByDepartment(@PathVariable String department) {
        log.info("Received request to get applications for department: {}", department);
        List<AppRegistry> apps = appRegistryService.getAppsByDepartment(department);
        return ResponseEntity.ok(apps);
    }

    /**
     * Endpoint to activate or deactivate an application
     * 
     * @param appId The unique identifier of the application
     * @param active Whether to activate (true) or deactivate (false)
     * @return The updated application
     */
    @PutMapping("/{appId}/status")
    public ResponseEntity<AppRegistry> setAppStatus(
            @PathVariable String appId,
            @RequestParam boolean active) {
        log.info("Received request to set application {} status to: {}", appId, active);
        AppRegistry updatedApp = appRegistryService.setAppStatus(appId, active);
        return ResponseEntity.ok(updatedApp);
    }

    /**
     * Endpoint to generate a new API key for an application
     * 
     * @param appId The unique identifier of the application
     * @return The updated application with new API key
     */
    @PostMapping("/{appId}/apikey")
    public ResponseEntity<AppRegistry> generateApiKey(@PathVariable String appId) {
        log.info("Received request to generate new API key for application: {}", appId);
        AppRegistry updatedApp = appRegistryService.generateApiKey(appId);
        return ResponseEntity.ok(updatedApp);
    }

    /**
     * Endpoint to validate an API key
     * 
     * @param apiKey The API key to validate
     * @return OK if valid, Unauthorized if invalid
     */
    @PostMapping("/validate-key")
    public ResponseEntity<Map<String, Boolean>> validateApiKey(@RequestParam String apiKey) {
        log.info("Received request to validate API key");
        boolean valid = appRegistryService.validateApiKey(apiKey);
        
        if (valid) {
            return ResponseEntity.ok(Map.of("valid", true));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("valid", false));
        }
    }
}
