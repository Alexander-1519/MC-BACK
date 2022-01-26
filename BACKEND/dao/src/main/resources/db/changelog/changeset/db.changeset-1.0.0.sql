CREATE TABLE roles(
    id              BIGSERIAL PRIMARY KEY,
    name            VARCHAR(64) NOT NULL
);

CREATE TABLE users(
        id              BIGSERIAL PRIMARY KEY,
        first_name      VARCHAR(255) NOT NULL,
        last_name       VARCHAR(255) NOT NULL,
        birthday        TIMESTAMP WITHOUT TIME ZONE NOT NULL,
        username        VARCHAR(255) NOT NULL,
        password        VARCHAR(255) NOT NULL,
        phone           VARCHAR(255) NOT NULL,
        email           VARCHAR(255) NOT NULL,
        imageUrl        VARCHAR(255) NOT NULL,
        role            BIGINT REFERENCES roles(id) NOT NULL,
        created_at      TIMESTAMP WITHOUT TIME ZONE NOT NULL,
        updated_at      TIMESTAMP WITHOUT TIME ZONE NOT NULL,
        created_by      VARCHAR(64) NOT NULL,
        updated_by      VARCHAR(64) NOT NULL
);