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
public class TokenRegistryClient {

    private static final Logger log = LoggerFactory.getLogger(TokenRegistryClient.class);

    private final RestTemplate restTemplate;

    public TokenRegistryClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${token-registry.api.base-url}")
    private String baseUrl;

    @Value("${token-registry.api.register-endpoint}")
    private String registerEndpoint;

    /**
     * Registers token and metadata with STT Token Registry
     */
    public boolean registerToken(String contractAddress, String metadata) {
        log.info("Registering token with STT Token Registry for contract address: {}", contractAddress);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("contract_address", contractAddress);
        requestBody.put("metadata", metadata);
        
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
        
        try {
            ResponseEntity<JsonNode> response = restTemplate.exchange(
                    baseUrl + registerEndpoint,
                    HttpMethod.POST,
                    requestEntity,
                    JsonNode.class
            );
            
            if (response.getStatusCode().is2xxSuccessful()) {
                log.info("Successfully registered token with STT Token Registry");
                return true;
            } else {
                log.error("Failed to register token: {}", response.getStatusCode());
                throw new RuntimeException("Failed to register token with STT Token Registry");
            }
        } catch (Exception e) {
            log.error("Error registering token", e);
            throw new RuntimeException("Failed to register token with STT Token Registry", e);
        }
    }
}
