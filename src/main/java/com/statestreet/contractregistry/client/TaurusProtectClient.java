package com.statestreet.contractregistry.client;

import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class TaurusProtectClient {

    private static final Logger log = LoggerFactory.getLogger(TaurusProtectClient.class);

    private final RestTemplate restTemplate;

    public TaurusProtectClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${taurus.api.base-url}")
    private String baseUrl;

    @Value("${taurus.api.auth-endpoint}")
    private String authEndpoint;

    @Value("${taurus.api.deploy-endpoint}")
    private String deployEndpoint;

    @Value("${taurus.api.request-endpoint}")
    private String requestEndpoint;

    @Value("${taurus.api.approve-endpoint}")
    private String approveEndpoint;

    @Value("${taurus.api.whitelist-endpoint}")
    private String whitelistEndpoint;

    @Value("${taurus.api.whitelist-approve-endpoint}")
    private String whitelistApproveEndpoint;

    @Value("${taurus.api.client-id}")
    private String clientId;

    @Value("${taurus.api.client-secret}")
    private String clientSecret;

    /**
     * Authenticates with Taurus Protect API and returns an auth token
     */
    public String getAuthToken() {
        log.info("Getting authentication token from Taurus Protect");
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("client_id", clientId);
        requestBody.put("client_secret", clientSecret);
        
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
        
        try {
            ResponseEntity<JsonNode> response = restTemplate.exchange(
                    baseUrl + authEndpoint,
                    HttpMethod.POST,
                    requestEntity,
                    JsonNode.class
            );
            
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                String token = response.getBody().path("access_token").asText();
                log.info("Successfully retrieved auth token");
                return token;
            } else {
                log.error("Failed to get auth token: {}", response.getStatusCode());
                throw new RuntimeException("Failed to authenticate with Taurus Protect API");
            }
        } catch (Exception e) {
            log.error("Error getting auth token", e);
            throw new RuntimeException("Failed to authenticate with Taurus Protect API", e);
        }
    }

    /**
     * Sends a deployment request to Taurus Protect API
     */
    public String deploySmartContract(String authToken, String contractBytecode, String contractName, String constructorArgs) {
        log.info("Sending deployment request to Taurus Protect for contract: {}", contractName);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("bytecode", contractBytecode);
        requestBody.put("name", contractName);
        
        if (constructorArgs != null && !constructorArgs.isEmpty()) {
            requestBody.put("constructor_args", constructorArgs);
        }
        
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
        
        try {
            ResponseEntity<JsonNode> response = restTemplate.exchange(
                    baseUrl + deployEndpoint,
                    HttpMethod.POST,
                    requestEntity,
                    JsonNode.class
            );
            
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                String requestId = response.getBody().path("request_id").asText();
                log.info("Successfully sent deployment request, received request ID: {}", requestId);
                return requestId;
            } else {
                log.error("Failed to deploy smart contract: {}", response.getStatusCode());
                throw new RuntimeException("Failed to deploy smart contract with Taurus Protect API");
            }
        } catch (Exception e) {
            log.error("Error deploying smart contract", e);
            throw new RuntimeException("Failed to deploy smart contract with Taurus Protect API", e);
        }
    }

    /**
     * Gets request details including hash and metadata
     */
    public Map<String, Object> getRequestDetails(String authToken, String requestId) {
        log.info("Getting request details for request ID: {}", requestId);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        
        HttpEntity<?> requestEntity = new HttpEntity<>(headers);
        
        try {
            ResponseEntity<JsonNode> response = restTemplate.exchange(
                    baseUrl + requestEndpoint + "?ids=" + requestId,
                    HttpMethod.GET,
                    requestEntity,
                    JsonNode.class
            );
            
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                JsonNode requestNode = response.getBody().path("requests").path(0);
                
                Map<String, Object> requestDetails = new HashMap<>();
                requestDetails.put("hash", requestNode.path("hash").asText());
                requestDetails.put("metadata", requestNode.path("metadata").toString());
                requestDetails.put("status", requestNode.path("status").asText());
                
                if (requestNode.has("whitelist_id")) {
                    requestDetails.put("whitelist_id", requestNode.path("whitelist_id").asText());
                }
                
                if (requestNode.has("contract_address")) {
                    requestDetails.put("contract_address", requestNode.path("contract_address").asText());
                }
                
                if (requestNode.has("transaction_hash")) {
                    requestDetails.put("transaction_hash", requestNode.path("transaction_hash").asText());
                }
                
                log.info("Successfully retrieved request details for request ID: {}", requestId);
                return requestDetails;
            } else {
                log.error("Failed to get request details: {}", response.getStatusCode());
                throw new RuntimeException("Failed to get request details from Taurus Protect API");
            }
        } catch (Exception e) {
            log.error("Error getting request details", e);
            throw new RuntimeException("Failed to get request details from Taurus Protect API", e);
        }
    }

    /**
     * Approves a deployment request with the signed hash
     */
    public String approveDeployment(String authToken, String requestId, String signedHash) {
        log.info("Approving deployment for request ID: {}", requestId);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("request_id", requestId);
        requestBody.put("signature", signedHash);
        
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
        
        try {
            ResponseEntity<JsonNode> response = restTemplate.exchange(
                    baseUrl + approveEndpoint,
                    HttpMethod.POST,
                    requestEntity,
                    JsonNode.class
            );
            
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                String approvedSignatures = response.getBody().path("signatures").toString();
                log.info("Successfully approved deployment for request ID: {}", requestId);
                return approvedSignatures;
            } else {
                log.error("Failed to approve deployment: {}", response.getStatusCode());
                throw new RuntimeException("Failed to approve deployment with Taurus Protect API");
            }
        } catch (Exception e) {
            log.error("Error approving deployment", e);
            throw new RuntimeException("Failed to approve deployment with Taurus Protect API", e);
        }
    }

    /**
     * Gets whitelist approval details
     */
    public Map<String, Object> getWhitelistApprovalDetails(String authToken, String whitelistId) {
        log.info("Getting whitelist approval details for whitelist ID: {}", whitelistId);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        
        HttpEntity<?> requestEntity = new HttpEntity<>(headers);
        
        try {
            ResponseEntity<JsonNode> response = restTemplate.exchange(
                    baseUrl + whitelistEndpoint + "?ids=" + whitelistId,
                    HttpMethod.GET,
                    requestEntity,
                    JsonNode.class
            );
            
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                JsonNode whitelistNode = response.getBody().path("whitelists").path(0);
                
                Map<String, Object> whitelistDetails = new HashMap<>();
                whitelistDetails.put("hash", whitelistNode.path("hash").asText());
                whitelistDetails.put("metadata", whitelistNode.path("metadata").toString());
                
                log.info("Successfully retrieved whitelist approval details for whitelist ID: {}", whitelistId);
                return whitelistDetails;
            } else {
                log.error("Failed to get whitelist approval details: {}", response.getStatusCode());
                throw new RuntimeException("Failed to get whitelist details from Taurus Protect API");
            }
        } catch (Exception e) {
            log.error("Error getting whitelist approval details", e);
            throw new RuntimeException("Failed to get whitelist details from Taurus Protect API", e);
        }
    }

    /**
     * Approves a whitelist with the signed hash
     */
    public String approveWhitelist(String authToken, String whitelistId, String signedHash) {
        log.info("Approving whitelist for whitelist ID: {}", whitelistId);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("whitelist_id", whitelistId);
        requestBody.put("signature", signedHash);
        
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
        
        try {
            ResponseEntity<JsonNode> response = restTemplate.exchange(
                    baseUrl + whitelistApproveEndpoint,
                    HttpMethod.POST,
                    requestEntity,
                    JsonNode.class
            );
            
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                String approvedSignatures = response.getBody().path("signatures").toString();
                log.info("Successfully approved whitelist for whitelist ID: {}", whitelistId);
                return approvedSignatures;
            } else {
                log.error("Failed to approve whitelist: {}", response.getStatusCode());
                throw new RuntimeException("Failed to approve whitelist with Taurus Protect API");
            }
        } catch (Exception e) {
            log.error("Error approving whitelist", e);
            throw new RuntimeException("Failed to approve whitelist with Taurus Protect API", e);
        }
    }
}
