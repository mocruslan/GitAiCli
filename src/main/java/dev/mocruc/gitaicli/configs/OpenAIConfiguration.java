package dev.mocruc.gitaicli.configs;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import dev.mocruc.gitaicli.apis.ChatGPTClient;
import dev.mocruc.gitaicli.services.OpenAIHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAIConfiguration {

    @Bean
    public OpenAIClient openAIClient(OpenAIHelper openAIHelper) {
        return new OpenAIOkHttpClient.Builder()
                .apiKey(openAIHelper.getAPIKey())
                .build();
    }

    @Bean
    public ChatGPTClient chatGPTClient(OpenAIClient openAIClient, OpenAIHelper openAIHelper) {
        return new ChatGPTClient(openAIClient, openAIHelper);
    }
}
