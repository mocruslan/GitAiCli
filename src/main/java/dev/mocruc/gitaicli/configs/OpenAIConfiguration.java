package dev.mocruc.gitaicli.configs;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import dev.mocruc.gitaicli.core.apis.OpenAiAPI;
import dev.mocruc.gitaicli.core.ai.helper.OpenAIHelper;
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
    public OpenAiAPI chatGPTClient(OpenAIClient openAIClient, OpenAIHelper openAIHelper) {
        return new OpenAiAPI(openAIClient, openAIHelper);
    }
}
