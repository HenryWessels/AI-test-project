package com.ginger.aitest.rest.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.api.OllamaModel;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ChatModel chatModel;

    public TestController(@Qualifier("ollamaChatModel") ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @PostMapping("/ask-question")
    public String askQuestion(@RequestBody String question) {

        logger.info("Question/ Prompt: {}", question);

        isQuestionValid(question);

        ChatResponse response = callOllama(question);

        logger.info("AI model Response: {}", response.toString());
        return response.getResults().getFirst().getOutput().getText();
    }

    private String isQuestionValid(String question) {
        ChatResponse response = callOllama("Please answer with a boolean Value. Would you be able to respond to this? " + question);

        logger.info("Is question valid: {}", response.getResult().getOutput().getText());
        return response.getResults().getFirst().getOutput().getText();
    }

    private ChatResponse callOllama(Prompt prompt) {
        logger.info("Calling Ollama: {}", prompt.getContents());
        return chatModel.call(prompt);
    }

    private ChatResponse callOllama(String Question) {
        return callOllama(buildOllamaPrompt(Question));
    }

    private Prompt buildOllamaPrompt(String question) {
        return new Prompt(question, getOllamaOptions());
    }

    private OllamaOptions getOllamaOptions() {
        return OllamaOptions.builder()
                .model(OllamaModel.LLAMA3)
                .temperature(0.4)
                .build();
    }
}
