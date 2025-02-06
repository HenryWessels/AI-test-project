package com.ginger.aitest.rest.controllers;

import com.ginger.aitest.core.services.ai.models.AiModel;
import com.ginger.aitest.infrastructure.aop.pointcuts.Logging;
import com.ginger.aitest.infrastructure.aop.pointcuts.ThreadName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final AiModel aiService;

    public TestController(AiModel model) {
        this.aiService = model;
    }

    @PostMapping("/ask-question")
    @Logging
    @ThreadName(customThreadName = "testing-thread-names", useCorrelationId = true)
    public String askQuestion(@RequestBody String question, @RequestHeader("X-Correlation-ID") String correlationId) {

        logger.info("Correlation-ID {} - Requested question: {}", correlationId, question);

        aiService.isQuestionValid(question);
        ChatResponse response = aiService.call(question);

        logger.info("AI model Response: {}", response.toString());
        return response.getResults().getFirst().getOutput().getText();
    }
}
