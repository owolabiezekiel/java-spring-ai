SELECT * FROM pg_available_extensions WHERE name = 'vector';
CREATE EXTENSION IF NOT EXISTS vector;
CREATE EXTENSION IF NOT EXISTS hstore;
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

DROP TABLE vector_store;

CREATE TABLE IF NOT EXISTS vector_store (
                                            id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
    content text,
    metadata json,
    embedding vector(1536)
    );

CREATE INDEX ON vector_store USING HNSW (embedding vector_cosine_ops);

SELECT id, content FROM vector_store;

SELECT COUNT(*) FROM vector_store WHERE content = 'This is a direct SQL test'