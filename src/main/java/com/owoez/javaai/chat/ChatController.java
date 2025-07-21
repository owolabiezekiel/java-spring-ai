package com.owoez.javaai.chat;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
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
        .user("Tell me an interesting fact about Java")
        .call().content();
  }

  @GetMapping("/stream")
  public Flux<String> stream(){
    return chatClient.prompt()
        .user("Tell me an interesting fact about Java")
        .stream().content();
  }

  @GetMapping("/joke")
  public ChatResponse joke(){
    return chatClient.prompt()
        .user("Tell me a dad joke")
        .call().chatResponse();
  }
}
