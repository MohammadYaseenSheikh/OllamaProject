package com.yaseen.code.deepseekproject.com.yaseen.code.deepseekproject.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class CallPrompt {

    private final RestTemplate restTemplate;
    private final String API_URL = "https://api.together.xyz/v1/chat/completions";
    private final String API_KEY = "c96aa6ba8ee79de73f410d1c50d685552c36868876230053ad17770947c0e72b";

    public CallPrompt(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getAIResponse(String prompt) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(API_KEY);

        Map<String, Object> requestBody = Map.of(
                "model", "meta-llama/Llama-3-8b-chat-hf",
                "messages", new Object[]{Map.of("role", "user", "content", prompt)},
                "temperature", 0.7
        );

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(API_URL, request, String.class);

        return response.getBody();
    }

}
