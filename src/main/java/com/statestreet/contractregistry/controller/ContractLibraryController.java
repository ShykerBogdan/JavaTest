package com.statestreet.contractregistry.controller;

import com.statestreet.contractregistry.entity.ContractLibrary;
import com.statestreet.contractregistry.service.ContractLibraryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * REST controller for contract library operations.
 * Handles API endpoints related to managing smart contract templates.
 */
@RestController
@RequestMapping("/api/contracts/library")
public class ContractLibraryController {

    private static final Logger log = LoggerFactory.getLogger(ContractLibraryController.class);

    private final ContractLibraryService contractLibraryService;

    /**
     * Constructor for dependency injection
     */
    public ContractLibraryController(ContractLibraryService contractLibraryService) {
        this.contractLibraryService = contractLibraryService;
    }

    /**
     * Endpoint to add a new contract to the library
     * 
     * @param contractLibrary The contract library entry to add
     * @return The saved contract library entry with generated ID
     */
    @PostMapping
    public ResponseEntity<ContractLibrary> addContract(@Valid @RequestBody ContractLibrary contractLibrary) {
        log.info("Received request to add contract to library: {}", contractLibrary.getName());
        ContractLibrary savedContract = contractLibraryService.addContract(contractLibrary);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedContract);
    }

    /**
     * Endpoint to update an existing contract in the library
     * 
     * @param id The ID of the contract to update
     * @param contractLibrary The updated contract library data
     * @return The updated contract library entry
     */
    @PutMapping("/{id}")
    public ResponseEntity<ContractLibrary> updateContract(
            @PathVariable Long id, 
            @Valid @RequestBody ContractLibrary contractLibrary) {
        log.info("Received request to update contract with ID: {}", id);
        ContractLibrary updatedContract = contractLibraryService.updateContract(id, contractLibrary);
        return ResponseEntity.ok(updatedContract);
    }

    /**
     * Endpoint to get a contract by its ID
     * 
     * @param id The ID of the contract to retrieve
     * @return The contract if found
     */
    @GetMapping("/{id}")
    public ResponseEntity<ContractLibrary> getContractById(@PathVariable Long id) {
        log.info("Received request to get contract with ID: {}", id);
        return contractLibraryService.getContractById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Endpoint to get a contract by its name
     * 
     * @param name The name of the contract to retrieve
     * @return The contract if found
     */
    @GetMapping("/name/{name}")
    public ResponseEntity<ContractLibrary> getContractByName(@PathVariable String name) {
        log.info("Received request to get contract with name: {}", name);
        return contractLibraryService.getContractByName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Endpoint to get all contracts in the library
     * 
     * @return List of all contracts
     */
    @GetMapping
    public ResponseEntity<List<ContractLibrary>> getAllContracts() {
        log.info("Received request to get all contracts from library");
        List<ContractLibrary> contracts = contractLibraryService.getAllContracts();
        return ResponseEntity.ok(contracts);
    }

    /**
     * Endpoint to delete a contract from the library
     * 
     * @param id The ID of the contract to delete
     * @return No content if deleted successfully
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContract(@PathVariable Long id) {
        log.info("Received request to delete contract with ID: {}", id);
        boolean deleted = contractLibraryService.deleteContract(id);
        
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Endpoint to validate a contract's source code and bytecode
     * 
     * @param contractLibrary The contract library entry to validate
     * @return OK if valid, Bad Request if invalid
     */
    @PostMapping("/validate")
    public ResponseEntity<String> validateContract(@RequestBody ContractLibrary contractLibrary) {
        log.info("Received request to validate contract: {}", contractLibrary.getName());
        boolean valid = contractLibraryService.validateContract(contractLibrary);
        
        if (valid) {
            return ResponseEntity.ok("Contract is valid");
        } else {
            return ResponseEntity.badRequest().body("Contract validation failed");
        }
    }
}
