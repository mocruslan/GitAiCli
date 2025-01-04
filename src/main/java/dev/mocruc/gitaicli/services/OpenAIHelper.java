package dev.mocruc.gitaicli.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class OpenAIHelper {
    private final Environment environment;

    @Autowired
    public OpenAIHelper(Environment environment) {
        this.environment = environment;
    }

    public String getAPIKey() {
        return environment.getProperty("openai.api-key");
    }
}
