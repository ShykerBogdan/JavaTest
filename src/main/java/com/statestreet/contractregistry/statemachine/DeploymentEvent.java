package com.statestreet.contractregistry.statemachine;

/**
 * Represents the various events that can trigger state transitions
 * in the smart contract deployment process
 */
public enum DeploymentEvent {
    AUTHENTICATE,
    AUTHENTICATION_SUCCESS,
    AUTHENTICATION_FAILURE,
    REQUEST_DEPLOYMENT,
    DEPLOYMENT_REQUEST_SUCCESS,
    DEPLOYMENT_REQUEST_FAILURE,
    REQUEST_APPROVAL,
    FETCH_HASH,
    HASH_FETCHED,
    SIGN_HASH,
    HASH_SIGNED,
    APPROVE_DEPLOYMENT,
    DEPLOYMENT_APPROVED,
    CHECK_DEPLOYMENT_STATUS,
    DEPLOYMENT_COMPLETED,
    REQUEST_WHITELIST,
    FETCH_WHITELIST_HASH,
    WHITELIST_HASH_FETCHED,
    SIGN_WHITELIST_HASH,
    WHITELIST_HASH_SIGNED,
    APPROVE_WHITELIST,
    WHITELIST_APPROVED,
    REGISTER_TOKEN,
    TOKEN_REGISTERED,
    ERROR_OCCURRED
}
