package dev.mocruc.gitaicli.services;

import dev.mocruc.gitaicli.apis.ChatGPTClient;
import dev.mocruc.gitaicli.data.Prompt;
import dev.mocruc.gitaicli.exceptions.ApiException;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AIService {
    private final ChatGPTClient chatGPTClient;
    private final PromptBuilder promptBuilder;

    public AIService(ChatGPTClient chatGPTClient, PromptBuilder promptBuilder) {
        this.chatGPTClient = chatGPTClient;
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
        return chatGPTClient.executePrompt(prompt)
                .orElseThrow(() -> new ApiException("No response received from LLM"));
    }
}
