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
public class HashSigningServiceClient {

    private static final Logger log = LoggerFactory.getLogger(HashSigningServiceClient.class);

    private final RestTemplate restTemplate;

    public HashSigningServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${hash-service.api.base-url}")
    private String baseUrl;

    @Value("${hash-service.api.sign-endpoint}")
    private String signEndpoint;

    /**
     * Sends hash and metadata to STT Hash Service for signing
     */
    public String signHash(String hash, String metadata) {
        log.info("Sending hash to STT Hash Service for signing");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("hash", hash);
        requestBody.put("metadata", metadata);

        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<JsonNode> response = restTemplate.exchange(
                    baseUrl + signEndpoint,
                    HttpMethod.POST,
                    requestEntity,
                    JsonNode.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                String signedHash = response.getBody().path("signed_hash").asText();
                log.info("Successfully received signed hash from STT Hash Service");
                return signedHash;
            } else {
                log.error("Failed to sign hash: {}", response.getStatusCode());
                throw new RuntimeException("Failed to sign hash with STT Hash Service");
            }
        } catch (Exception e) {
            log.error("Error signing hash", e);
            throw new RuntimeException("Failed to sign hash with STT Hash Service", e);
        }
    }
}
