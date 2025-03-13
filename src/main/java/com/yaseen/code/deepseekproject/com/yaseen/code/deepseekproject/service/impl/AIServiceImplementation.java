package com.yaseen.code.deepseekproject.com.yaseen.code.deepseekproject.service.impl;

import com.yaseen.code.deepseekproject.com.yaseen.code.deepseekproject.model.CVResponse;
import com.yaseen.code.deepseekproject.com.yaseen.code.deepseekproject.model.PromptResponse;
import com.yaseen.code.deepseekproject.com.yaseen.code.deepseekproject.service.AIService;
import com.yaseen.code.deepseekproject.com.yaseen.code.deepseekproject.utils.CallPrompt;
import com.yaseen.code.deepseekproject.com.yaseen.code.deepseekproject.utils.ExtractPDFText;
import com.yaseen.code.deepseekproject.com.yaseen.code.deepseekproject.utils.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class AIServiceImplementation implements AIService {

    private final CallPrompt callPrompt;
    private final ExtractPDFText extractPDFText;

    @Autowired
    public AIServiceImplementation(CallPrompt callPrompt, ExtractPDFText extractPDFText) {
        this.callPrompt = callPrompt;
        this.extractPDFText = extractPDFText;
    }

    @Override
    public PromptResponse getLiteResponse(String prompt) {
        String response = callPrompt.getAIResponse(prompt);
        return new PromptResponse(JsonParser.parseResponse(response).choices.getFirst().message.content);
    }

    @Override
    public CVResponse evaluateResume(MultipartFile resume, String jobDescription) throws IOException {
        String resumeText = extractPDFText.extractTextFromPDF(resume);
        String analysis = callPrompt.getAICVResponse(resumeText, jobDescription);
        String response = JsonParser.parseResponse(analysis).choices.getFirst().message.content;

        return new CVResponse("", response);
    }

}
