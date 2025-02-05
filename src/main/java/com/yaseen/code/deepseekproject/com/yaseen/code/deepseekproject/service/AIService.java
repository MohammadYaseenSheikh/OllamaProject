package com.yaseen.code.deepseekproject.com.yaseen.code.deepseekproject.service;

import com.yaseen.code.deepseekproject.com.yaseen.code.deepseekproject.model.CVResponse;
import com.yaseen.code.deepseekproject.com.yaseen.code.deepseekproject.model.PromptResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface AIService {

    PromptResponse getLiteResponse(String prompt);
    CVResponse evaluateResume(MultipartFile resume, String jobDescription) throws IOException;

}
