package com.owoez.javaai.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "gemini")
public class GeminiConfig {
  private String embeddingModel;
  private String projectId;
  private String location;
}
