CREATE TABLE addresses
(
    id              BIGSERIAL PRIMARY KEY,
    street          VARCHAR(255) NOT NULL,
    city_id         BIGINT REFERENCES cities(id) NOT NULL,
    created_at      TIMESTAMP WITHOUT TIME ZONE,
    updated_at      TIMESTAMP WITHOUT TIME ZONE,
    created_by      VARCHAR(64),
    updated_by      VARCHAR(64)
)