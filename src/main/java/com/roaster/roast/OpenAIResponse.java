package com.roaster.roast;

import java.util.List;

public record OpenAIResponse(
        List<Choice> choices
) {
    public record Choice(Message message) {
        public record Message(String content) {}
    }
}
