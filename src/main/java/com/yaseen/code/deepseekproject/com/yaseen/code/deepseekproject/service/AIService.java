package com.yaseen.code.deepseekproject.com.yaseen.code.deepseekproject.service;

import org.springframework.stereotype.Service;

@Service
public interface AIService {
    String getAIResponse(String prompt);
}
