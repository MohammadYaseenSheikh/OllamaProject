package com.yaseen.code.deepseekproject.com.yaseen.code.deepseekproject.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class CallPrompt {

    private static final Logger logger = LoggerFactory.getLogger(CallPrompt.class);
    private final RestTemplate restTemplate;
    private final String API_URL = "https://api.openai.com/v1/chat/completions";

    @Value("${OPEN_AI_API_KEY}")
    private String API_KEY;

    @Value("${AI_LITE_MODEL}")
    private String AI_MODEL;

    @Value("${AI_ADVANCE_MODEL}")
    private String AI_ADV_MODEL;

    public CallPrompt(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getAIResponse(String prompt) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(API_KEY);

        Map<String, Object> requestBody = Map.of(
                "model", AI_MODEL,
                "messages", new Object[]{
                        Map.of("role", "system", "content", "You are a helpful assistant."),
                        Map.of("role", "user", "content", prompt)
                }
        );

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(API_URL, request, String.class);
            return response.getBody();
        } catch (Exception e) {
            logger.error("Error while fetching AI response: ", e);
            return "Error: Unable to fetch response from OpenAI API.";
        }
    }

    public String getAICVResponse(String resumeText, String jobDescription) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(API_KEY);

        Map<String, Object> requestBody = Map.of(
                "model", AI_ADV_MODEL,
                "messages", new Object[]{
                        Map.of("role", "system", "content",
                                "You are an AI that compares a job description with a resume. " +
                                        "Your response must be a JSON object with \"score\" (1-10) and \"suggestions\" (list of improvements). " +
                                        "Do NOT include any text outside JSON format."),
                        Map.of("role", "user", "content",
                                "Compare the following resume and job description:\n\n" +
                                        "Resume:\n" + resumeText + "\n\n" +
                                        "Job Description:\n" + jobDescription + "\n\n" +
                                        "Return only a JSON object like this:\n" +
                                        "{\"score\": 8, \"suggestions\": [\"Improve skills in Python\", \"Add leadership experience\"]}")
                },
                "temperature", 0.3,
                "top_p", 0.9,
                "max_tokens", 500
        );

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(API_URL, request, String.class);
            return response.getBody();
        } catch (Exception e) {
            logger.error("Error while fetching AI CV response: ", e);
            return "Error: Unable to fetch response from OpenAI API.";
        }
    }
}
