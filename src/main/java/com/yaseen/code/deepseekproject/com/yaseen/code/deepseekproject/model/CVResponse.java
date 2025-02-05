package com.yaseen.code.deepseekproject.com.yaseen.code.deepseekproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CVResponse {

    private String score;
    private String suggestion;
    private String errorMessage;

    public CVResponse(String score, String suggestion) {
        this.score = score;
        this.suggestion = suggestion;
    }

    public CVResponse(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
