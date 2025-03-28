package com.statestreet.contractregistry.repository;

import com.statestreet.contractregistry.model.SmartContractDeployment;
import com.statestreet.contractregistry.statemachine.DeploymentState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SmartContractDeploymentRepository extends JpaRepository<SmartContractDeployment, Long> {
    
    Optional<SmartContractDeployment> findByRequestId(String requestId);
    
    List<SmartContractDeployment> findByCurrentState(DeploymentState state);
    
    Optional<SmartContractDeployment> findByContractAddress(String contractAddress);
}
