CREATE TABLE maintenances
(
        id              BIGSERIAL PRIMARY KEY,
        name            VARCHAR(255) NOT NULL,
        price           DECIMAL NOT NULL,
        master_id       BIGINT REFERENCES masters(id) NOT NULL,
        created_at      TIMESTAMP WITHOUT TIME ZONE,
        updated_at      TIMESTAMP WITHOUT TIME ZONE,
        created_by      VARCHAR(64),
        updated_by      VARCHAR(64)
)