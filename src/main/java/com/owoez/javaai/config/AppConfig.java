package com.owoez.javaai.config;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.vectorstore.pgvector.PgVectorStore;
import org.springframework.ai.vertexai.embedding.VertexAiEmbeddingConnectionDetails;
import org.springframework.ai.vertexai.embedding.text.VertexAiTextEmbeddingModel;
import org.springframework.ai.vertexai.embedding.text.VertexAiTextEmbeddingOptions;
import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@RequiredArgsConstructor
public class AppConfig {
  public static final Logger log = LoggerFactory.getLogger(AppConfig.class);

  private final GeminiConfig geminiConfig;

  @Bean
  public ChatClient openAiChatClient(OpenAiChatModel model) {
    return ChatClient.create(model);
  }

  @Bean
  public ChatClient geminiChatClient(VertexAiGeminiChatModel model) {
    return ChatClient.create(model);
  }

  @Bean
  public EmbeddingModel geminiTextEmbeddingModel() {
    VertexAiEmbeddingConnectionDetails connectionDetails =
        VertexAiEmbeddingConnectionDetails.builder()
            .projectId(geminiConfig.getProjectId())
            .location(geminiConfig.getLocation())
            .build();

    VertexAiTextEmbeddingOptions options = VertexAiTextEmbeddingOptions.builder()
        .model(geminiConfig.getEmbeddingModel())
        .build();

    return new VertexAiTextEmbeddingModel(connectionDetails, options);
  }

  @Bean
  public PgVectorStore pgVectorStore(@Qualifier("geminiTextEmbeddingModel") EmbeddingModel embeddingModel,
                                     JdbcTemplate jdbcTemplate) {
    return PgVectorStore.builder(jdbcTemplate, embeddingModel)
        .build();
  }
}
