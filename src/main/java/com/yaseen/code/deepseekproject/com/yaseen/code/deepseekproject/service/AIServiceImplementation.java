package com.yaseen.code.deepseekproject.com.yaseen.code.deepseekproject.service;

import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.stereotype.Service;

@Service
public class AIServiceImplementation implements AIService {

    private final OllamaChatModel chatModel;

    public AIServiceImplementation(OllamaChatModel ollamaChatModel) {
        this.chatModel = ollamaChatModel;
    }

    public String getAIResponse(String prompt) {
        return chatModel.call(prompt);
    }
}
