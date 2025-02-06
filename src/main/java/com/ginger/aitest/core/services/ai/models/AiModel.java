package com.ginger.aitest.core.services.ai.models;

import org.springframework.ai.chat.model.ChatResponse;

public interface AiModel {

    void isQuestionValid(String question);
    ChatResponse call(String question);
}
