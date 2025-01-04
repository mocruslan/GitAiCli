package dev.mocruc.gitaicli.apis;

import com.openai.client.OpenAIClient;
import com.openai.errors.OpenAIException;
import com.openai.models.*;
import dev.mocruc.gitaicli.data.Prompt;
import dev.mocruc.gitaicli.exceptions.ApiException;
import dev.mocruc.gitaicli.services.OpenAIHelper;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Optional;


/**
 * ChatGPTClient is a singleton class that facilitates interaction with OpenAI's API.
 * It uses an instance of OpenAIOkHttpClient for establishing and managing the connection.
 * This class ensures that there is only one instance of the client throughout the application.
 * <br/><br/>
 * <a href="https://github.com/openai/openai-java">Github documentation</a>
 */
@Component
public class ChatGPTClient {
    private static final Logger logger = LoggerFactory.getLogger(ChatGPTClient.class);
    private final OpenAIClient openAIClient;
    private final OpenAIHelper openAIHelper;

    public ChatGPTClient(OpenAIClient openAIClient, OpenAIHelper openAIHelper) {
        this.openAIClient = openAIClient;
        this.openAIHelper = openAIHelper;
    }

    public Optional<String> executePrompt(Prompt prompt) throws ApiException {
        try {
            ChatCompletionCreateParams completionCreateParams = ChatCompletionCreateParams.builder()
                    .model(ChatModel.GPT_4O_MINI)
                    .maxTokens(1024)
                    .addMessage(ChatCompletionMessageParam.ofChatCompletionUserMessageParam(
                            ChatCompletionUserMessageParam.builder()
                                    .role(ChatCompletionUserMessageParam.Role.USER)
                                    .content(ChatCompletionUserMessageParam.Content.ofTextContent(prompt.toString()))
                                    .build()))
                    .build();
            ChatCompletion chatCompletion = openAIClient.chat().completions().create(completionCreateParams);
            return chatCompletion.choices().get(0).message().content();
        } catch (OpenAIException ex) {
            logger.error(ex.getMessage());
            throw new ApiException("Unable to execute prompt!");
        }
    }
}
