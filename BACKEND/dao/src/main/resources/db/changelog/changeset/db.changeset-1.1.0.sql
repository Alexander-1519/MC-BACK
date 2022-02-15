CREATE TABLE masters(
    id              BIGSERIAL PRIMARY KEY,
    started_at      TIMESTAMP WITHOUT TIME ZONE,
    info            VARCHAR(1024),
    category        VARCHAR(64),
    user_id         BIGINT REFERENCES users(id) NOT NULL,
    created_at      TIMESTAMP WITHOUT TIME ZONE,
    updated_at      TIMESTAMP WITHOUT TIME ZONE,
    created_by      VARCHAR(64),
    updated_by      VARCHAR(64)
);

CREATE TABLE regions(
        id              BIGSERIAL PRIMARY KEY,
        name            VARCHAR(255),
        created_at      TIMESTAMP WITHOUT TIME ZONE,
        updated_at      TIMESTAMP WITHOUT TIME ZONE,
        created_by      VARCHAR(64),
        updated_by      VARCHAR(64)
);

CREATE TABLE cities(
        id              BIGSERIAL PRIMARY KEY,
        name            VARCHAR(255),
        region_id       BIGINT REFERENCES regions(id),
        created_at      TIMESTAMP WITHOUT TIME ZONE,
        updated_at      TIMESTAMP WITHOUT TIME ZONE,
        created_by      VARCHAR(64),
        updated_by      VARCHAR(64)
);