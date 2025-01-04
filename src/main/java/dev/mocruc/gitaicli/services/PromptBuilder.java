package dev.mocruc.gitaicli.services;

import dev.mocruc.gitaicli.data.Prompt;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PromptBuilder {
    public Prompt buildRecentChangesSummaryPrompt(String recentCommits) {
        return Prompt.builder()
                .backgroundInfo(PromptMessageConst.BackgroundInformation.PROMPT_BACKGROUND_DEVELOPER)
                .request(PromptMessageConst.Request.PROMPT_REQUEST_RECENT_CHANGES)
                .responseFormat(PromptMessageConst.ResponseFormat.PROMPT_RESPONSE_FORMAT_RECENT_CHANGES)
                .data(recentCommits)
                .build();
    }

    public Prompt buildCommitMessagePrompt(Map<String, String> changes) {
        return Prompt.builder()
                .backgroundInfo(PromptMessageConst.BackgroundInformation.PROMPT_BACKGROUND_DEVELOPER)
                .request(PromptMessageConst.Request.PROMPT_REQUEST_GENERATE_COMMIT_MESSAGE)
                .responseFormat(PromptMessageConst.ResponseFormat.PROMPT_RESPONSE_FORMAT_COMMIT_MESSAGE)
                .data(changes.toString())
                .build();
    }
}
