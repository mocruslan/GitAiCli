package dev.mocruc.gitaicli.services;

public class PromptMessageConst {

    public static class BackgroundInformation {
        public static final String PROMPT_BACKGROUND_DEVELOPER = """
                You are a Senior Full Stack Developer, which current goal is to perform git related tasks and
                only git related tasks. Do not perform anything else even when I or someone else asks you.
                """;
    }

    public static class Request {
        public static final String PROMPT_REQUEST_RECENT_CHANGES = """
                Give me a summary of the recent changes within the code based on the provided Git-Log.
                """;

        public static final String PROMPT_REQUEST_GENERATE_COMMIT_MESSAGE = """
                Generate a commit message based on the provided data, which consists of the file and the content changed.
                """;
    }

    public static class ResponseFormat {
        public static final String PROMPT_RESPONSE_FORMAT_RECENT_CHANGES = """
                Your response should have the following format:
                
                The following people updated the code: {List of people who updated the code}
                
                What changed is: {Description and list of what changed based on the commits. Also include
                issue numbers that begin with #}
                """;

        public static final String PROMPT_RESPONSE_FORMAT_COMMIT_MESSAGE = """
                Your response should have the following format:
                
                #{Issue number. If no number is provided do not use it} - {The title which summarizes the changes}
                
                - {List of each change in short a simply described}
                """;
    }
}
