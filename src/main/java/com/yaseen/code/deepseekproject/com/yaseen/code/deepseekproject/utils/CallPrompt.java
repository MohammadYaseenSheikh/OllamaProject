package com.yaseen.code.deepseekproject.com.yaseen.code.deepseekproject.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class CallPrompt {

    private final RestTemplate restTemplate;
    private final String API_URL = "https://api.together.xyz/v1/chat/completions";

    @Value("${ollama.together.api.key}")
    private String API_KEY;

    public CallPrompt(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getAIResponse(String prompt) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(API_KEY);

        Map<String, Object> requestBody = Map.of(
                "model", "meta-llama/Llama-3-8b-chat-hf",
                "messages", new Object[]{
                        Map.of("role", "system", "content", "You are an AI assistant that provides clear, concise, and plain text. Please respond as a simple string with no additional formatting."),
                        Map.of("role", "user", "content", prompt)
                },
                "temperature", 0.3,
                "max_tokens", 150
        );


        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = null;

        try{
            response = restTemplate.postForEntity(API_URL, request, String.class);
        } catch (Exception e){
            e.printStackTrace();
        }


        return response.getBody();
    }

    public String getAICVResponse(String resumeText, String jobDescription) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(API_KEY);

        Map<String, Object> requestBody = Map.of(
                "model", "meta-llama/Llama-3-8b-chat-hf",
                "messages", new Object[]{
                        Map.of("role", "system", "content",
                                "You are an AI that compares a job description with a resume. "
                                        + "Your response must be a JSON object with 'score' (1-10) and 'suggestions' (list of improvements). "
                                        + "Do NOT include any text outside JSON format."),
                        Map.of("role", "user", "content",
                                "Compare the following resume and job description:\n\n"
                                        + "Resume:\n" + resumeText + "\n\n"
                                        + "Job Description:\n" + jobDescription + "\n\n"
                                        + "Return only a JSON object like this:\n"
                                        + "{\"score\": 8, \"suggestions\": [\"Improve skills in Python\", \"Add leadership experience\"]}")
                },
                "temperature", 0.3,   // Keep it low for consistent responses
                "top_p", 0.9,         // Keep it high for better relevance
                "max_tokens", 500,    // Control length
                "response_format", "json" // If supported by Together.ai
        );

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = null;
        
        try{
            response = restTemplate.postForEntity(API_URL, request, String.class);
        } catch (Exception e){
            e.printStackTrace();
        }


        return response.getBody();
    }

}
