package com.owoez.javaai.rag;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RagController {
  private final ChatClient chatClient;
  private final QuestionAnswerAdvisor advisor;

  public RagController(@Qualifier("geminiChatClient") ChatClient chatClient,
                       @Qualifier("pgVectorStore") VectorStore vectorStore) {
    this.chatClient = chatClient;
    this.advisor = new QuestionAnswerAdvisor(vectorStore);
  }

  @GetMapping("/rag/models")
  public Models chat(@RequestParam(value = "message", defaultValue = "Give me a list of all the models from OpenAI") String message){
    return chatClient.prompt()
        .advisors(advisor)
        .user(message)
        .call().entity(Models.class);
  }

  @GetMapping("/rag/qa")
  public String qa(@RequestParam(value = "message", defaultValue = "Which company created the Spring framework?") String message) {
    return chatClient.prompt()
        .user(message)
        .advisors(advisor)
        .call()
        .content();
  }
}
