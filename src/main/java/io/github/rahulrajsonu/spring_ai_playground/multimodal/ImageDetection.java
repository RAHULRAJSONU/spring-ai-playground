package io.github.rahulrajsonu.spring_ai_playground.multimodal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.io.Resource;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ImageDetection {

    private final Logger log = LoggerFactory.getLogger(ImageDetection.class);

    private final ChatClient chatClient;

    public ImageDetection(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @PostMapping("/image/describe")
    public String describeImage(@RequestParam("media") MultipartFile media) {
        log.info("Received image for description: {}, size: {}", media.getOriginalFilename(), media.getSize());
        if(media.isEmpty()) {
            return "Please upload a valid image file.";
        }
        Resource resource = media.getResource();
        return chatClient.prompt()
                .user(u->{
                    u.text("Can you please describe the image I am uploading?");
                    u.media(MimeTypeUtils.IMAGE_JPEG, resource);
                })
                .call()
                .content();
    }

}
