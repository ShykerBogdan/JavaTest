package com.statestreet.contractregistry.service;

import com.statestreet.contractregistry.entity.ContractLibrary;
import com.statestreet.contractregistry.exception.ResourceNotFoundException;
import com.statestreet.contractregistry.repository.ContractLibraryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the ContractLibraryService interface.
 * Handles operations related to the contract library management.
 */
@Service
public class ContractLibraryServiceImpl implements ContractLibraryService {

    private static final Logger log = LoggerFactory.getLogger(ContractLibraryServiceImpl.class);
    
    private final ContractLibraryRepository contractLibraryRepository;
    
    /**
     * Constructor for dependency injection
     */
    public ContractLibraryServiceImpl(ContractLibraryRepository contractLibraryRepository) {
        this.contractLibraryRepository = contractLibraryRepository;
    }

    @Override
    @Transactional
    public ContractLibrary addContract(ContractLibrary contractLibrary) {
        log.info("Adding new contract to library: {}", contractLibrary.getName());
        
        // Check if contract with same name already exists
        if (contractLibraryRepository.existsByName(contractLibrary.getName())) {
            log.error("Contract with name {} already exists", contractLibrary.getName());
            throw new IllegalArgumentException("Contract with name " + contractLibrary.getName() + " already exists");
        }
        
        // Validate the contract before saving
        if (!validateContract(contractLibrary)) {
            log.error("Contract validation failed for: {}", contractLibrary.getName());
            throw new IllegalArgumentException("Contract validation failed");
        }
        
        ContractLibrary savedContract = contractLibraryRepository.save(contractLibrary);
        log.info("Contract added to library with ID: {}", savedContract.getId());
        
        return savedContract;
    }

    @Override
    @Transactional
    public ContractLibrary updateContract(Long id, ContractLibrary contractLibrary) {
        log.info("Updating contract in library with ID: {}", id);
        
        // Check if contract exists
        ContractLibrary existingContract = contractLibraryRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Contract not found with ID: {}", id);
                    return new ResourceNotFoundException("Contract not found with ID: " + id);
                });
        
        // Check if trying to update to a name that already exists (and is not this contract)
        if (!existingContract.getName().equals(contractLibrary.getName()) && 
                contractLibraryRepository.existsByName(contractLibrary.getName())) {
            log.error("Cannot update contract, name {} is already in use", contractLibrary.getName());
            throw new IllegalArgumentException("Contract name already in use: " + contractLibrary.getName());
        }
        
        // Validate the updated contract
        if (!validateContract(contractLibrary)) {
            log.error("Updated contract validation failed for: {}", contractLibrary.getName());
            throw new IllegalArgumentException("Contract validation failed");
        }
        
        // Update fields
        existingContract.setName(contractLibrary.getName());
        existingContract.setVersion(contractLibrary.getVersion());
        existingContract.setSourceCode(contractLibrary.getSourceCode());
        existingContract.setAbi(contractLibrary.getAbi());
        existingContract.setBytecode(contractLibrary.getBytecode());
        
        ContractLibrary updatedContract = contractLibraryRepository.save(existingContract);
        log.info("Contract updated in library: {}", updatedContract.getName());
        
        return updatedContract;
    }

    @Override
    public Optional<ContractLibrary> getContractById(Long id) {
        log.info("Getting contract by ID: {}", id);
        return contractLibraryRepository.findById(id);
    }

    @Override
    public Optional<ContractLibrary> getContractByName(String name) {
        log.info("Getting contract by name: {}", name);
        return Optional.ofNullable(contractLibraryRepository.findByName(name));
    }

    @Override
    public List<ContractLibrary> getAllContracts() {
        log.info("Getting all contracts from library");
        return contractLibraryRepository.findAll();
    }

    @Override
    @Transactional
    public boolean deleteContract(Long id) {
        log.info("Deleting contract with ID: {}", id);
        
        if (contractLibraryRepository.existsById(id)) {
            contractLibraryRepository.deleteById(id);
            log.info("Contract deleted with ID: {}", id);
            return true;
        } else {
            log.error("Contract not found with ID: {}", id);
            return false;
        }
    }

    @Override
    public boolean validateContract(ContractLibrary contractLibrary) {
        log.info("Validating contract: {}", contractLibrary.getName());
        
        // Basic validation
        if (contractLibrary.getName() == null || contractLibrary.getName().trim().isEmpty()) {
            log.error("Contract name cannot be empty");
            return false;
        }
        
        if (contractLibrary.getVersion() == null || contractLibrary.getVersion().trim().isEmpty()) {
            log.error("Contract version cannot be empty");
            return false;
        }
        
        if (contractLibrary.getSourceCode() == null || contractLibrary.getSourceCode().trim().isEmpty()) {
            log.error("Contract source code cannot be empty");
            return false;
        }
        
        if (contractLibrary.getAbi() == null || contractLibrary.getAbi().trim().isEmpty()) {
            log.error("Contract ABI cannot be empty");
            return false;
        }
        
        if (contractLibrary.getBytecode() == null || contractLibrary.getBytecode().trim().isEmpty()) {
            log.error("Contract bytecode cannot be empty");
            return false;
        }
        
        // In a real implementation, you might use a compiler service or external validation
        // For this example, we just do basic checks
        
        log.info("Contract validation successful for: {}", contractLibrary.getName());
        return true;
    }
}
