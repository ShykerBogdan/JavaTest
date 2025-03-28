package com.statestreet.contractregistry.statemachine;

/**
 * Represents the various states in the smart contract deployment process
 */
public enum DeploymentState {
    INITIAL,
    AUTHENTICATED,
    DEPLOY_REQUESTED,
    APPROVAL_PENDING,
    HASH_RETRIEVED,
    HASH_SIGNED,
    DEPLOYMENT_APPROVED,
    DEPLOYED,
    WHITELIST_REQUESTED,
    WHITELIST_HASH_RETRIEVED,
    WHITELIST_HASH_SIGNED,
    WHITELIST_APPROVED,
    TOKEN_REGISTERED,
    COMPLETED,
    ERROR
}
