package io.github.rahulrajsonu.spring_ai_playground;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication()
@EnableAutoConfiguration(exclude = {
        org.springframework.ai.model.ollama.autoconfigure.OllamaChatAutoConfiguration.class,
        org.springframework.ai.model.openai.autoconfigure.OpenAiEmbeddingAutoConfiguration.class
})
public class SpringAiPlaygroundApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringAiPlaygroundApplication.class, args);
	}

}
