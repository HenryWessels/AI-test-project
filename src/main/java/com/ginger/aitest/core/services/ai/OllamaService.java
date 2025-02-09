package com.ginger.aitest.core.services.ai;

import com.ginger.aitest.configuration.OllamaProperties;
import com.ginger.aitest.core.services.ai.models.OllamaServiceModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class OllamaService implements OllamaServiceModel {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ChatModel chatModel;
    private final OllamaProperties ollamaProperties;

    public OllamaService(@Qualifier("ollamaChatModel") ChatModel chatModel, OllamaProperties ollamaProperties) {
        this.chatModel = chatModel;
        this.ollamaProperties = ollamaProperties;

        logger.debug("OllamaService created using Model: {}",
                ollamaProperties.getModelString() != null ? ollamaProperties.getModelString() : ollamaProperties.getModel().getName());
    }

    public void isQuestionValid(String question) {
        ChatResponse response = call("Please ONLY answer with a boolean Value. Would you be able to respond to the following? \"" + question + "\"");

        logger.debug("Is question valid: {}", response.getResult().getOutput().getText());
    }

    public ChatResponse call(Prompt prompt) {
        logger.debug("Calling Ollama: {}", prompt.getContents());
        ChatResponse response = chatModel.call(prompt);
        logger.trace("AI model Response: {}", response.toString());
        logger.debug("Question answer: {}", response.getResult().getOutput().getText());
        return response;
    }

    public ChatResponse call(String Question) {
        return call(buildOllamaPrompt("Please answer the following question if possible. \"" + Question + "\""));
    }

    private Prompt buildOllamaPrompt(String question) {
        return new Prompt(question, getOllamaOptions());
    }

    private OllamaOptions getOllamaOptions() {
        if (ollamaProperties.getModelString() == null)
            return OllamaOptions.builder()
                .model(ollamaProperties.getModel())
                .temperature(ollamaProperties.getTemperature())
                .build();
        return OllamaOptions.builder()
                .model(ollamaProperties.getModelString())
                .temperature(ollamaProperties.getTemperature())
                .build();
    }
}
