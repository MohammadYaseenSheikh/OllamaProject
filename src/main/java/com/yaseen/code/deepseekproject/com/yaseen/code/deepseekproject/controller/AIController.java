package com.yaseen.code.deepseekproject.com.yaseen.code.deepseekproject.controller;

import com.yaseen.code.deepseekproject.com.yaseen.code.deepseekproject.model.CVResponse;
import com.yaseen.code.deepseekproject.com.yaseen.code.deepseekproject.model.PromptRequest;
import com.yaseen.code.deepseekproject.com.yaseen.code.deepseekproject.model.PromptResponse;
import com.yaseen.code.deepseekproject.com.yaseen.code.deepseekproject.service.AIServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/Ollama")
public class AIController {

    @Autowired
    private AIServiceImplementation service;

    @PostMapping(value = "/lite", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PromptResponse> chat(@RequestBody PromptRequest request) {
        PromptResponse response = new PromptResponse("Something went wrong!");
        String prompt = request.getPrompt();
        if (prompt == null || prompt.isEmpty()) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(service.getLiteResponse(prompt), HttpStatus.OK);
    }

    @PostMapping(
            value = "/resume-scanner",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CVResponse> chat(
            @RequestParam("file") MultipartFile resume,
            @RequestParam("job-description") String jobDescription
    ){
        CVResponse response = new CVResponse("","","Something went wrong!");
        if(resume.getSize() > 5 * 1024 * 1024){
            response.setErrorMessage("File size exceeds the 5MB limit.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        try{
            return new ResponseEntity<>(service.evaluateResume(resume, jobDescription), HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

}
