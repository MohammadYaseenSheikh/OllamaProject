package com.yaseen.code.deepseekproject.com.yaseen.code.deepseekproject.controller;

import com.yaseen.code.deepseekproject.com.yaseen.code.deepseekproject.utils.ResumeReader;
import com.yaseen.code.deepseekproject.com.yaseen.code.deepseekproject.service.AIServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/Ollama")
public class AIController {

    @Autowired
    private ResumeReader utility;

    private final AIServiceImplementation aiService;

    public AIController(AIServiceImplementation aiService) {
        this.aiService = aiService;
    }

    @PostMapping("/lite")
    public String chat(@RequestParam String prompt) {
        return aiService.getAIResponse(prompt);
    }

    @PostMapping(
            value = "/resume-scanner",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String chat(
            @RequestParam("file") MultipartFile resume,
            @RequestParam("job-description") String jobDescription
    ){
        if(resume.getSize() > 5 * 1024 * 1024){
            return "File size exceeds the 5MB limit.";
        }
        try{
            String resumeText = utility.extractTextFromPDF(resume);
            return utility.evaluateResume(resumeText, jobDescription);
        } catch (IOException e) {
            return "Oops! Something went wrong.";
        }
    }

}
