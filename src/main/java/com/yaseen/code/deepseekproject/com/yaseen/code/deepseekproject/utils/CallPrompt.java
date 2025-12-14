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
    private final String API_URL = "https://api.openai.com/v1/chat/completions";

    @Value("${OPEN_AI_API_KEY}")
    private String API_KEY;

    public CallPrompt(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getAIResponse(String prompt) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(API_KEY);

        Map<String, Object> requestBody = Map.of(
                "model", "gpt-5.2",
                "messages", new Object[]{
                        Map.of("role", "developer", "content", "You are a helpful assistant."),
                        Map.of("role", "user", "content", prompt)
                }
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
                "model", "gpt-5.2",
                "messages", new Object[]{
                        Map.of("role", "system", "content",
                                "You are an AI that compares a job description with a resume. "
                                        + "Your response must be a JSON object with \\\"score\\\" (1-10) and \\\"suggestions\\\" (list of improvements). "
                                        + "Do NOT include any text outside JSON format."),
                        Map.of("role", "user", "content",
                                "Compare the following resume and job description:\\n\\n"
                                        + "Resume:\\n" + resumeText + "\\n\\n"
                                        + "Job Description:\\n" + jobDescription + "\\n\\n"
                                        + "Return only a JSON object like this:\\n"
                                        + "{\\\"score\\\": 8, \\\"suggestions\\\": [\\\"Improve skills in Python\\\", \\\"Add leadership experience\\\"]}" )
                },
                "temperature", 0.3,
                "top_p", 0.9,
                "max_tokens", 500
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
