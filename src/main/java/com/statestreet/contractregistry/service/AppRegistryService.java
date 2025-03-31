package com.statestreet.contractregistry.service;

import com.statestreet.contractregistry.entity.AppRegistry;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing application registry operations.
 * Handles logic for storing and retrieving registered applications.
 */
public interface AppRegistryService {

    /**
     * Registers a new application
     *
     * @param appRegistry The application to register
     * @return The registered application with generated ID
     */
    AppRegistry registerApp(AppRegistry appRegistry);

    /**
     * Updates an existing registered application
     *
     * @param id The ID of the application to update
     * @param appRegistry The updated application data
     * @return The updated application
     */
    AppRegistry updateApp(Long id, AppRegistry appRegistry);

    /**
     * Retrieves an application by its ID
     *
     * @param id The ID of the application to retrieve
     * @return Optional containing the application if found
     */
    Optional<AppRegistry> getAppById(Long id);

    /**
     * Retrieves an application by its unique app ID
     *
     * @param appId The unique identifier of the application
     * @return Optional containing the application if found
     */
    Optional<AppRegistry> getAppByAppId(String appId);

    /**
     * Retrieves an application by its name
     *
     * @param appName The name of the application
     * @return Optional containing the application if found
     */
    Optional<AppRegistry> getAppByName(String appName);

    /**
     * Retrieves all registered applications
     *
     * @return List of all applications
     */
    List<AppRegistry> getAllApps();

    /**
     * Retrieves all applications owned by a specific department
     *
     * @param department The department that owns the applications
     * @return List of applications owned by the department
     */
    List<AppRegistry> getAppsByDepartment(String department);

    /**
     * Activates or deactivates an application
     *
     * @param appId The unique identifier of the application
     * @param active Whether to activate (true) or deactivate (false)
     * @return The updated application
     */
    AppRegistry setAppStatus(String appId, boolean active);

    /**
     * Generates a new API key for an application
     *
     * @param appId The unique identifier of the application
     * @return The updated application with new API key
     */
    AppRegistry generateApiKey(String appId);

    /**
     * Checks if an API key is valid and belongs to an active application
     *
     * @param apiKey The API key to validate
     * @return true if valid, false otherwise
     */
    boolean validateApiKey(String apiKey);
}
