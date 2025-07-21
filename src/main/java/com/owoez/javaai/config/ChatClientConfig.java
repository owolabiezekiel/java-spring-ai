package com.owoez.javaai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {
   @Bean
   public ChatClient openAiChatClient(OpenAiChatModel model) {
     return ChatClient.create(model);
   }

   @Bean
   public ChatClient geminiChatClient(VertexAiGeminiChatModel model) {
     return ChatClient.create(model);
   }
}
