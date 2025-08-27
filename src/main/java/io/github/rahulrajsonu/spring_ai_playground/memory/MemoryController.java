package io.github.rahulrajsonu.spring_ai_playground.memory;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemoryController {

    private final ChatClient chatClient;

    public MemoryController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/memory")
    public String memory(@RequestParam String message) {
        return chatClient.prompt()
                .user(message)
                .call()
                .content();
    }
}
