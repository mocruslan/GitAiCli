package dev.mocruc.gitaicli.core.ai.services;

import dev.mocruc.gitaicli.core.apis.OpenAiAPI;
import dev.mocruc.gitaicli.shared.data.Prompt;
import dev.mocruc.gitaicli.services.PromptBuilder;
import dev.mocruc.gitaicli.shared.exceptions.ApiException;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AIService {
    private final OpenAiAPI openAiAPI;
    private final PromptBuilder promptBuilder;

    public AIService(OpenAiAPI openAiAPI, PromptBuilder promptBuilder) {
        this.openAiAPI = openAiAPI;
        this.promptBuilder = promptBuilder;
    }

    public String generateCommitSummary(String recentCommits) throws ApiException {
        Prompt prompt = promptBuilder.buildRecentChangesSummaryPrompt(recentCommits);
        return executePrompt(prompt);
    }

    public String generateCommitMessage(Map<String, String> changes) throws ApiException {
        Prompt prompt = promptBuilder.buildCommitMessagePrompt(changes);
        return executePrompt(prompt);
    }

    private String executePrompt(Prompt prompt) throws ApiException {
        return openAiAPI.executePrompt(prompt)
                .orElseThrow(() -> new ApiException("No response received from LLM"));
    }
}
