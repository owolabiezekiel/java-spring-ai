# Access your container:
docker exec -it your_postgres_container_name bash

# Install build tools and Postgres dev headers:
apt-get update && apt-get install -y make gcc postgresql-server-dev-15 git

# Clone and build PG Vector
git clone https://github.com/pgvector/pgvector.git
cd pgvector || exit
apt update && apt install -y clang-11
make clean
clean
make install
