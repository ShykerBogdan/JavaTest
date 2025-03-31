package com.statestreet.contractregistry.repository;

import com.statestreet.contractregistry.entity.ContractRegistry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing contract registry data.
 * Handles storage and retrieval of deployed smart contracts information.
 */
@Repository
public interface ContractRegistryRepository extends JpaRepository<ContractRegistry, Long> {
    
    /**
     * Find a contract by its blockchain address
     * 
     * @param contractAddress The blockchain address of the contract
     * @return Optional of the contract if found
     */
    Optional<ContractRegistry> findByContractAddress(String contractAddress);
    
    /**
     * Find all contracts with a specific name
     * 
     * @param contractName The name of the contract
     * @return List of contracts with the given name
     */
    List<ContractRegistry> findByContractName(String contractName);
    
    /**
     * Find all contracts deployed on a specific network
     * 
     * @param network The blockchain network name
     * @return List of contracts on the given network
     */
    List<ContractRegistry> findByNetwork(String network);
    
    /**
     * Find all contracts owned by a specific address
     * 
     * @param owner The owner's blockchain address
     * @return List of contracts owned by the given address
     */
    List<ContractRegistry> findByOwner(String owner);
    
    /**
     * Check if a contract exists at a specific address
     * 
     * @param contractAddress The blockchain address to check
     * @return True if exists, false otherwise
     */
    boolean existsByContractAddress(String contractAddress);
    
    /**
     * Find all whitelisted contracts
     * 
     * @return List of whitelisted contracts
     */
    List<ContractRegistry> findByWhitelistedTrue();
}
