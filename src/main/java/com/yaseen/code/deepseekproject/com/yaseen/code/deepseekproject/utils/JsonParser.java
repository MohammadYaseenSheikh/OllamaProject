package com.yaseen.code.deepseekproject.com.yaseen.code.deepseekproject.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yaseen.code.deepseekproject.com.yaseen.code.deepseekproject.model.TogetherAIResponse;

public class JsonParser {
    public static TogetherAIResponse parseResponse(String jsonResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(jsonResponse, TogetherAIResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse JSON", e);
        }
    }
}

