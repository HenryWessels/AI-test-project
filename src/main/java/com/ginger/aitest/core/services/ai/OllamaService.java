package com.ginger.aitest.core.services.ai;

import com.ginger.aitest.core.services.ai.models.OllamaServiceModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.api.OllamaModel;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class OllamaService implements OllamaServiceModel {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ChatModel chatModel;

    public OllamaService(@Qualifier("ollamaChatModel") ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public void isQuestionValid(String question) {
        ChatResponse response = call("Please ONLY answer with a boolean Value. Would you be able to respond to the following? \"" + question + "\"");

        logger.info("Is question valid: {}", response.getResult().getOutput().getText());
    }

    public ChatResponse call(Prompt prompt) {
        logger.info("Calling Ollama: {}", prompt.getContents());
        return chatModel.call(prompt);
    }

    public ChatResponse call(String Question) {
        return call(buildOllamaPrompt(Question));
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
