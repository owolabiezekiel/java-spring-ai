package com.owoez.javaai;

import com.owoez.javaai.config.AppConfig;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.reader.JsonReader;
import org.springframework.ai.vectorstore.pgvector.PgVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

  public static final Logger log = LoggerFactory.getLogger(AppConfig.class);

  private final PgVectorStore pgVectorStore;
  private final JdbcTemplate jdbcTemplate;

  @Value("classpath:data/models.json")
  private Resource resource;

  @Override
  @Transactional
  public void run(String... args) {
    // Check if the database is already populated
    Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM vector_store", Integer.class);
    if (count != null && count > 0) {
      log.info("Database is already populated with {} models. Skipping data loading.", count);
      return;
    }

    log.info("Loading data from {} into the vector store...", resource.getFilename());
    JsonReader reader = new JsonReader(resource, "company", "model");
    pgVectorStore.add(reader.get());
    log.info("Data loading complete.");
  }
}