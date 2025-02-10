package com.ginger.aitest.rest.controllers;

import com.ginger.aitest.core.services.ai.models.AiModel;
import com.ginger.aitest.infrastructure.aop.pointcutAnnotations.Logging;
import com.ginger.aitest.infrastructure.aop.pointcutAnnotations.ThreadName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ai")
public class AiController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final AiModel aiService;

    public AiController(AiModel model) {
        this.aiService = model;
    }

    @PostMapping("/ask-question")
    @Logging
    @ThreadName(customThreadName = "testing-thread-names", useCorrelationId = true)
    public String askQuestion(@RequestBody String question, @RequestHeader("X-Correlation-ID") String correlationId) {
        logger.info("Requested question: {}", question);

        aiService.isQuestionValid(question);
        ChatResponse response = aiService.call(question);

        logger.info("Question answer: {}", response.getResult().getOutput().getText());
        return response.getResult().getOutput().getText();
    }
}
