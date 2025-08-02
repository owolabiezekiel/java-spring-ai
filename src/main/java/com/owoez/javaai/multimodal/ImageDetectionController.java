package com.owoez.javaai.multimodal;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.image.ImageOptions;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ImageDetectionController {
  private final ChatClient chatClient;
  private final OpenAiImageModel imageModel;

  public ImageDetectionController(@Qualifier("geminiChatClient") ChatClient chatClient,
                                  OpenAiImageModel imageModel) {
    this.chatClient = chatClient;
    this.imageModel = imageModel;
  }

  @GetMapping("/image-to-text")
  public String imageToText(@RequestParam(value = "link") String link) {
    var system = """
        Image Description Guidelines:
        
        1. Length & Purpose: Generate not more than 50-word description of the image. If it contains the picture of a popular person, make sure to mention the person
        
        2. Content Requirements:
           - The image url will be supplied. if it is a valid url, find the image and describe it, else report that it is not a valid url.
           - If the link doesnt contain an image, dont describe it. Instead report that the link doesnt contain an image
        
        3. Tone & Style:
           - Write in an informative and warm tone
        """;


    return chatClient.prompt()
        .user(u -> {
          u.text("Please describe the image in the link {link}");
          u.param("link", link);
        })
        .system(system)
        .call().content();
  }

  @GetMapping("/generate-image")
  public ResponseEntity<Map<String, String>> structured(@RequestParam(defaultValue = "A redefined FC Barcelona logo") String prompt) {
    ImageOptions options = OpenAiImageOptions.builder()
        .model("dall-e-3")
        .width(720)
        .height(480)
        .quality("hd")
        .style("natural")
        .build();
    var imagePrompt = new ImagePrompt(prompt, options);
    ImageResponse imageResponse = imageModel.call(imagePrompt);
    String url = imageResponse.getResult().getOutput().getUrl();
    return ResponseEntity.ok(Map.of("prompt", prompt, "imageUrl", url));
  }

}
