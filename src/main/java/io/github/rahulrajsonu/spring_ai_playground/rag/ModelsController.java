package io.github.rahulrajsonu.spring_ai_playground.rag;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ModelsController {

    @Autowired
    private ChatClient chatClient;
    @Autowired
    private VectorStore vectorStore;

    @GetMapping("/rag/models")
    public Models faq(
            @RequestParam(value = "message", defaultValue = "give me the list of all models from openai along with their context window size") String message) {
        return chatClient.prompt()
                .advisors(new QuestionAnswerAdvisor(vectorStore))
                .user(message)
                .call()
                .entity(Models.class);
    }
}
