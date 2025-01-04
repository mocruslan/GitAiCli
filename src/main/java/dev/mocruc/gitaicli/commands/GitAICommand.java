package dev.mocruc.gitaicli.commands;

import dev.mocruc.gitaicli.exceptions.ApiException;
import dev.mocruc.gitaicli.services.AIService;
import dev.mocruc.gitaicli.services.GitService;
import org.springframework.shell.command.annotation.Command;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.Map;

@Component
@Command(command = "git-ai")
public class GitAICommand {
    private final GitService gitService;
    private final AIService aiService;

    public GitAICommand(GitService gitService, AIService aiService) {
        this.gitService = gitService;
        this.aiService = aiService;
    }

    @Command(command = "sum-changes", description = "Summarize recent changes (default last 7 days)")
    public String sumChanges() {
        try {
            String recentCommits = gitService.getRecentCommits(Path.of("."), 10);
            return aiService.generateCommitSummary(recentCommits);
        } catch (ApiException e) {
            return "Error: " + e.getMessage();
        }
    }

    @Command(command = "gen-commit-message", description = "Summarize recent changes")
    public String genCommitMessage() {
        try {
            Map<String, String> uncommittedChanges = gitService.getUncommittedChanges();
            return aiService.generateCommitMessage(uncommittedChanges);
        } catch (ApiException e) {
            return "Error: " + e.getMessage();
        }
    }
}
