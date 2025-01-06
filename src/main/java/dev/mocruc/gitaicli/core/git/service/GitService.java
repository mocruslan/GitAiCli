package dev.mocruc.gitaicli.core.git.service;

import dev.mocruc.gitaicli.core.git.helper.GitHelper;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.LogCommand;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GitService {
    public String getRecentCommits(Path repoPath, int limit) {
        StringBuilder gitHistory = new StringBuilder();

        try(Git git = Git.open(repoPath.toFile())) {
            LogCommand logCommand = git.log().setMaxCount(limit);

            logCommand.call().forEach(commit -> {
                gitHistory.append("Commit: ").append(commit.getName()).append("\n");
                gitHistory.append("Author: ").append(commit.getAuthorIdent().getName()).append("\n");
                gitHistory.append("Date: ").append(commit.getAuthorIdent().getWhen()).append("\n");
                gitHistory.append("Message: ").append(commit.getFullMessage()).append("\n\n");
            });
        } catch (IOException | GitAPIException e) {
            throw new RuntimeException(e);
        }

        return gitHistory.toString();
    }

    public Map<String, String> getUncommittedChanges() {
        Map<String, String> fileChanges = new HashMap<>();

        try(Repository repository = GitHelper.getRepository()) {
            try (Git git = new Git(repository)) {
                Status status = git.status().call();

                for (String modifiedFile : status.getModified()) {
                    retrieveChangesForModifiedFile(modifiedFile, repository, fileChanges);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return fileChanges;
    }

    private static void retrieveChangesForModifiedFile(String modifiedFile, Repository repository, Map<String, String> fileChanges) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        DiffFormatter formatter = new DiffFormatter(outputStream);
        formatter.setRepository(repository);

        ObjectReader reader = repository.newObjectReader();
        CanonicalTreeParser oldTreeIter = new CanonicalTreeParser();
        oldTreeIter.reset(reader, repository.resolve("HEAD^{tree}"));
        FileTreeIterator newTreeIter = new FileTreeIterator(repository);

        List<DiffEntry> diffs = formatter.scan(oldTreeIter, newTreeIter);

        for (DiffEntry diff : diffs) {
            if (diff.getNewPath().equals(modifiedFile)) {
                formatter.format(diff);
                String diffText = outputStream.toString(StandardCharsets.UTF_8);
                fileChanges.put(modifiedFile, diffText);
                break;
            }
        }

        formatter.close();
        outputStream.close();
    }
}
