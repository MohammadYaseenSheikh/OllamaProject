package com.yaseen.code.deepseekproject.com.yaseen.code.deepseekproject.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TogetherAIResponse {
    public String id;
    public String object;
    public long created;
    public String model;
    public List<Choice> choices;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Choice {
        public int index;
        public String finish_reason;
        public Message message;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Message {
        public String role;
        public String content;
    }
}
