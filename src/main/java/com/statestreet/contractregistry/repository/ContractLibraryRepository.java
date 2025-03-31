package com.statestreet.contractregistry.repository;

import com.statestreet.contractregistry.entity.ContractLibrary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing contract library data.
 * Manages storage and retrieval of smart contract library templates.
 */
@Repository
public interface ContractLibraryRepository extends JpaRepository<ContractLibrary, Long> {
    
    /**
     * Find a contract library entry by its name
     * 
     * @param name Name of the contract library
     * @return Contract library entry if found
     */
    ContractLibrary findByName(String name);
    
    /**
     * Check if a contract library exists by name
     * 
     * @param name Name of the contract library
     * @return True if exists, false otherwise
     */
    boolean existsByName(String name);
}
