CREATE TABLE maintenance_date
(
    id              BIGSERIAL PRIMARY KEY,
    date            TIMESTAMP WITHOUT TIME ZONE,
    master_id       BIGINT REFERENCES masters(id) NOT NULL,
    created_at      TIMESTAMP WITHOUT TIME ZONE,
    updated_at      TIMESTAMP WITHOUT TIME ZONE,
    created_by      VARCHAR(64),
    updated_by      VARCHAR(64)
)