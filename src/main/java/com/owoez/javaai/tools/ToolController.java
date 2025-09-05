package com.owoez.javaai.tools;

import com.owoez.javaai.model.ResponseModel;
import com.owoez.javaai.model.StateModel;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ResponseEntity;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatOptions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
public class ToolController {
  private final ChatClient chatClient;
  private final StateTools stateTools;

  public ToolController(@Qualifier("geminiChatClient") ChatClient chatClient,
                        StateTools stateTools) {
    this.chatClient = chatClient;
    this.stateTools = stateTools;
  }

  @GetMapping("/tools")
  public String tools(){
    return chatClient.prompt("Using the DateTime tool, calculate the date the user asks for")
        .user("What is tomorrows's date")
        .tools(new DateTimeTools())
        .call()
        .content();
  }

  @GetMapping("/tools/state")
  public Object stateTools(){
    return chatClient.prompt()
        .user("List the states that are in the system. convert to JSON. also include the count")
        .tools(stateTools)
        .call()
        .content();
  }

  @GetMapping("/tools/state/create")
  public String createState(String message){
    return chatClient.prompt()
        .user(message)
        .tools(stateTools)
        .call()
        .content();
  }

//  @GetMapping("/tools/state/details")
//  public ResponseModel<List<StateModel>> stateDetailsTools(){
//    var options = VertexAiGeminiChatOptions.builder()
//        .internalToolExecutionEnabled(true)
//        .toolName("getStates")
//        .build();
//
//    return chatClient.prompt()
//        .tools(stateTools)
//        .options(options)
//        .system("Always call the tool `getStates` to fetch data. Do not generate sample JSON.")
//        .user("Get the states in the system.")
//        .call()
//        .entity(new ParameterizedTypeReference<ResponseModel<List<StateModel>>>() {});
//  }
}
