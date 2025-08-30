package io.github.rahulrajsonu.spring_ai_playground.rag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Configuration
public class RagConfiguration {
    private static final Logger log = LoggerFactory.getLogger(RagConfiguration.class);
    private final String vectorStoreName = "vectorstore.json";

    @Value("classpath:data/models.json")
    private Resource models;

    @Bean
    public ApplicationRunner seedVectorStore(VectorStore vectorStore) {
        return args -> {
            log.info("Seeding pgvector from classpath:data/models.json (if not already present)...");
            TextReader reader = new TextReader(models);
            reader.getCustomMetadata().put("fileName", "models.json");
            List<Document> docs = reader.get();
            List<Document> chunks = new TokenTextSplitter().apply(docs);
            if (!chunks.isEmpty()) {
                vectorStore.add(chunks); // idempotency is up to your VectorStore impl; add a hash if needed
                log.info("Seeded {} chunks.", chunks.size());
            }
        };
    }

//    @Bean
    public SimpleVectorStore simpleVectorStore(EmbeddingModel embeddingModel){
        var simpleVectorStore = SimpleVectorStore.builder(embeddingModel).build();
        var vectorStoreFile = getVectorStoreFile();
        if(vectorStoreFile.exists()){
            log.info("Vector store already exists at: {}", vectorStoreFile.getAbsolutePath());
            simpleVectorStore.load(vectorStoreFile);
        }else {
            log.info("Creating new vector store at: {}", vectorStoreFile.getAbsolutePath());
            TextReader textReader = new TextReader(models);
            textReader.getCustomMetadata().put("fileName", "models.txt");
            List<Document> documents = textReader.get();
            TokenTextSplitter tokenTextSplitter = new TokenTextSplitter();
            List<Document> splitDocs = tokenTextSplitter.apply(documents);
            simpleVectorStore.add(splitDocs);
            simpleVectorStore.save(vectorStoreFile);
        }
        return simpleVectorStore;
    }

    private File getVectorStoreFile(){
        Path path = Paths.get("src","main","resources","data");
        String absolutePath = path.toFile().getAbsolutePath()+"/"+vectorStoreName;
        return new File(absolutePath);
    }

}
