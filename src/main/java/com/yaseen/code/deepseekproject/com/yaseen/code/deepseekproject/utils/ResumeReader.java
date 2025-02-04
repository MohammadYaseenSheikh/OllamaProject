package com.yaseen.code.deepseekproject.com.yaseen.code.deepseekproject.utils;

import com.yaseen.code.deepseekproject.com.yaseen.code.deepseekproject.service.AIServiceImplementation;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public class ResumeReader {

    private final AIServiceImplementation aiSerivce;

    public ResumeReader(AIServiceImplementation aiSerivce) {
        this.aiSerivce = aiSerivce;
    }

    public String extractTextFromPDF(MultipartFile file) throws IOException {
        try (PDDocument document = PDDocument.load(file.getInputStream())) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            return pdfStripper.getText(document);
        }
    }

    public String evaluateResume(String resumeText, String jobDescription) {

        String prompt =
                "Evaluate the following resume against this job description and a formatted answer in syntax like below:\n"
                +"Score: [Score as per the matching with the job description, Also match in range of 1 to 10 depending on the match.]/10\n"
                +"Area of improvement(Optional only if score is less than 10) Just mention the skills missing or lack in bullet points make sure its conent is less than 100 words only & keep the content focused on what could be imported only \n" +
                "Below are the inputs from User:"
                + "Job Description:\n" + jobDescription + "\n\n"
                + "Resume:\n" + resumeText;

        return aiSerivce.getAIResponse(prompt);

    }


}
