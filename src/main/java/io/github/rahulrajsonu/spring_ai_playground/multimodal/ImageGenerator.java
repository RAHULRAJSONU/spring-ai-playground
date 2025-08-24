package io.github.rahulrajsonu.spring_ai_playground.multimodal;

import org.springframework.ai.image.ImageOptions;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ImageGenerator {

    private final OpenAiImageModel imageModel;

    public ImageGenerator(OpenAiImageModel imageModel) {
        this.imageModel = imageModel;
    }

    @GetMapping("/image/generate")
    public ResponseEntity<Map<String, String>> generateImage(@RequestParam String imageDescription) {
        ImageOptions options = OpenAiImageOptions.builder()
                .model("dall-e-3")
                .width(1024)
                .height(1024)
                .quality("hd")
                .style("vivid")
                .build();

        ImagePrompt imagePrompt = new ImagePrompt(imageDescription, options);
        ImageResponse res = imageModel.call(imagePrompt);

        String url = res.getResult().getOutput().getUrl();
        return ResponseEntity.ok(
                Map.of(
                        "prompt", imageDescription,
                        "imageUrl", url

                )
        );
    }
}
