package com.statestreet.contractregistry.service;

import com.statestreet.contractregistry.entity.ContractLibrary;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing contract library operations.
 * Handles logic for storing and retrieving smart contract templates.
 */
public interface ContractLibraryService {

    /**
     * Adds a new contract to the library
     *
     * @param contractLibrary The contract library entry to add
     * @return The saved contract library entry with generated ID
     */
    ContractLibrary addContract(ContractLibrary contractLibrary);

    /**
     * Updates an existing contract in the library
     *
     * @param id The ID of the contract to update
     * @param contractLibrary The updated contract library data
     * @return The updated contract library entry
     */
    ContractLibrary updateContract(Long id, ContractLibrary contractLibrary);

    /**
     * Retrieves a contract by its ID
     *
     * @param id The ID of the contract to retrieve
     * @return Optional containing the contract if found
     */
    Optional<ContractLibrary> getContractById(Long id);

    /**
     * Retrieves a contract by its name
     *
     * @param name The name of the contract to retrieve
     * @return Optional containing the contract if found
     */
    Optional<ContractLibrary> getContractByName(String name);

    /**
     * Retrieves all contracts in the library
     *
     * @return List of all contracts
     */
    List<ContractLibrary> getAllContracts();

    /**
     * Deletes a contract from the library
     *
     * @param id The ID of the contract to delete
     * @return true if deleted successfully, false otherwise
     */
    boolean deleteContract(Long id);

    /**
     * Validates a contract's source code and bytecode
     *
     * @param contractLibrary The contract library entry to validate
     * @return true if valid, false otherwise
     */
    boolean validateContract(ContractLibrary contractLibrary);
}
