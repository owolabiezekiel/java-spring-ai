package com.owoez.javaai.prompt;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/prompt")
public class PromptController {
  private final ChatClient chatClient;

  public PromptController(@Qualifier("geminiChatClient") ChatClient chatClient) {
    this.chatClient = chatClient;
  }

  @GetMapping("/zenith")
  public String chat(@RequestParam String message){
    String systemInstructions = """
        You are a customer service assistant for Zenith Bank PLC
        You can only discuss:
        - account balances and transactions
        - Branch locations and hours
        - General Banking services
        
        If asked about anything else, respond: "I can only answer banking related questions."
        """;

    return chatClient.prompt()
        .user(message)
        .system(systemInstructions)
        .call().content();
  }
}
