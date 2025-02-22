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

    @Autowired
    private CallPrompt callPrompt;

    @Autowired
    private ExtractPDFText extractPDFText;

    @Override
    public PromptResponse getLiteResponse(String prompt) {
        String response = callPrompt.getAIResponse(prompt);
        return new PromptResponse(JsonParser.parseResponse(response).choices.getFirst().message.content);
    }

    @Override
    public CVResponse evaluateResume(MultipartFile resume, String jobDescription) throws IOException {
        String resumeText = extractPDFText.extractTextFromPDF(resume);
        String prompt =
                "Evaluate the following resume against the provided job description. Please provide the output in below format shown below Only." +
                        "\nFollow strict below format only No extra input before or after the given format." +
                        "Score: [Only Score]/10,\n" +
                        "Suggestion: [Only areas to improve with in 100 words only.]\n" +
                        "\n" +
                        "Below are the inputs:\n" +
                        "- Job Description:\n" +
                        jobDescription +
                        "\n" +
                        "- Resume:\n" +
                        resumeText +
                        "\n" +
                        "Make sure to:\n" +
                        "- Be consistent in scoring based on relevance to the job description.\n" +
                        "- Only mention areas of improvement if the score is less than 10, and keep them concise and actionable.\n" +
                        "- Don't mention generic statements; focus solely on the **specific skills and qualifications** that are relevant to the job description and what needs to be improved for a better match.\n";
        String response = JsonParser.parseResponse(callPrompt.getAIResponse(prompt)).choices.getFirst().message.content;
//        String[] result = response.split("\n\n");

        return new CVResponse("", response);
    }

}
