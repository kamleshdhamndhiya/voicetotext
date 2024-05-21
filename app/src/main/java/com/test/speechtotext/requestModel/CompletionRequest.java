package com.test.speechtotext.requestModel;

import java.util.List;

public class CompletionRequest {
    private String model;
    private List<Message> messages;
    private double temperature;
    private int max_tokens;
    private double top_p;
    private double frequency_penalty;
    private double presence_penalty;
    private List<String> stop;

    // Constructor, getters, and setters
    public CompletionRequest(String model, List<Message> messages, double temperature, int max_tokens, double top_p, double frequency_penalty, double presence_penalty, List<String> stop) {
        this.model = model;
        this.messages = messages;
        this.temperature = temperature;
        this.max_tokens = max_tokens;
        this.top_p = top_p;
        this.frequency_penalty = frequency_penalty;
        this.presence_penalty = presence_penalty;
        this.stop = stop;
    }

    // Add getters and setters here...
}

