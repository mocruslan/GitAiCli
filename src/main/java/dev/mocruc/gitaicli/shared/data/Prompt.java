package dev.mocruc.gitaicli.shared.data;


import lombok.Builder;

@Builder
public class Prompt {
    private final String backgroundInfo;
    private final String request;
    private final String responseFormat;
    private final String data;

    @Override
    public String toString() {
        return String.format("Your background is the following: %s. " +
                "I want you to perform the following task: %s. " +
                "You response for the task should look like this: %s" +
                "This is the data you can work with: %s ", backgroundInfo, request, responseFormat, data);
    }
}
