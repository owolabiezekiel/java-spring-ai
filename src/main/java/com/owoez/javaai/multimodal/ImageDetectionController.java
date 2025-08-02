package com.owoez.javaai.multimodal;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageDetectionController {
  private final ChatClient chatClient;

  public ImageDetectionController(@Qualifier("geminiChatClient") ChatClient chatClient) {
    this.chatClient = chatClient;
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

//  @GetMapping("/vacation/structured")
//  public Itinerary structured(@RequestParam(value = "country", defaultValue = "Nigeria") String country,
//                        @RequestParam(value = "count", defaultValue = "5") String count) {
//    return chatClient.prompt()
//        .user(u -> {
//          u.text("I want to travel to {country}, give me a list of {count} things to do");
//          u.params(Map.of("country", country, "count", count));
//        })
//        .call().entity(Itinerary.class);
//  }

}
