package dev.mocruc.gitaicli.core.git.helper;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class GitHelper {
    public static Repository getRepository() throws IOException {
        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        return builder
                .readEnvironment()
                .findGitDir()
                .build();
    }
}
