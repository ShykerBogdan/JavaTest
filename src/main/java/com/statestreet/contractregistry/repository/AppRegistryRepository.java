package com.statestreet.contractregistry.repository;

import com.statestreet.contractregistry.entity.AppRegistry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing application registry data.
 * Handles storage and retrieval of registered applications that can interact with smart contracts.
 */
@Repository
public interface AppRegistryRepository extends JpaRepository<AppRegistry, Long> {
    
    /**
     * Find an application by its unique ID
     * 
     * @param appId The unique identifier of the application
     * @return Optional of the application if found
     */
    Optional<AppRegistry> findByAppId(String appId);
    
    /**
     * Find an application by its name
     * 
     * @param appName The name of the application
     * @return Optional of the application if found
     */
    Optional<AppRegistry> findByAppName(String appName);
    
    /**
     * Find all applications owned by a specific department
     * 
     * @param ownerDepartment The department that owns the applications
     * @return List of applications owned by the department
     */
    List<AppRegistry> findByOwnerDepartment(String ownerDepartment);
    
    /**
     * Find all active applications
     * 
     * @return List of active applications
     */
    List<AppRegistry> findByActiveTrue();
    
    /**
     * Check if an application exists with the given ID
     * 
     * @param appId The application ID to check
     * @return True if exists, false otherwise
     */
    boolean existsByAppId(String appId);
    
    /**
     * Find an application by its API key
     * 
     * @param apiKey The API key of the application
     * @return Optional of the application if found
     */
    Optional<AppRegistry> findByApiKey(String apiKey);
}
