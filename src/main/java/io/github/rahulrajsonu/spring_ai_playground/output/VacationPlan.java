package io.github.rahulrajsonu.spring_ai_playground.output;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VacationPlan {

    private final ChatClient chatClient;

    public VacationPlan(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @GetMapping("/vacation/structured")
    public Itinerary structured(@RequestParam String message){
        return chatClient.prompt()
                .user(message)
                .call()
                .entity(Itinerary.class);
    }
}
