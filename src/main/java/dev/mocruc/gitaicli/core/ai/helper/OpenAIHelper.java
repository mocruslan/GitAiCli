package dev.mocruc.gitaicli.core.ai.helper;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class OpenAIHelper {
    private final Environment environment;

    public OpenAIHelper(Environment environment) {
        this.environment = environment;
    }

    public String getAPIKey() {
        return environment.getProperty("openai.api-key");
    }
}
