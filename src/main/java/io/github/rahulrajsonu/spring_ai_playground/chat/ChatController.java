package io.github.rahulrajsonu.spring_ai_playground.chat;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @GetMapping("/chat")
    public String chat(){
        return chatClient.prompt()
                .user("Tell me the future of java in the AI world")
                .call()
                .content();
    }

    @GetMapping("/chat/stream")
    public Flux<String> stream(){
        return chatClient.prompt()
                .user("Tell me the significance of Spring AI, and popular DL, ML, and AI frameworks in the Java ecosystem")
                .stream()
                .content();
    }

}
