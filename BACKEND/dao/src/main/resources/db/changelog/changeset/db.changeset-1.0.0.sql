CREATE TABLE roles(
    id              BIGSERIAL PRIMARY KEY,
    name            VARCHAR(64) NOT NULL
);

INSERT INTO roles (id, name) VALUES(1, 'ROLE_USER');
INSERT INTO roles (id, name) VALUES(2, 'ROLE_ADMIN');
INSERT INTO roles (id, name) VALUES(3, 'ROLE_MODERATOR');

CREATE TABLE users(
        id              BIGSERIAL PRIMARY KEY,
        first_name      VARCHAR(255),
        last_name       VARCHAR(255),
        birthday        TIMESTAMP WITHOUT TIME ZONE,
        username        VARCHAR(255) NOT NULL,
        password        VARCHAR(255) NOt NULL,
        phone           VARCHAR(255),
        email           VARCHAR(255) NOT NULL,
        image_url       VARCHAR(255),
        role_id         BIGINT REFERENCES roles(id) NOT NULL,
        created_at      TIMESTAMP WITHOUT TIME ZONE NOT NULL,
        updated_at      TIMESTAMP WITHOUT TIME ZONE NOT NULL,
        created_by      VARCHAR(64) NOT NULL,
        updated_by      VARCHAR(64) NOT NULL
);