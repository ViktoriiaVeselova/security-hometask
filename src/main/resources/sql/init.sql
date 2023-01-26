CREATE TABLE users (
  id BIGSERIAL PRIMARY KEY,
  email VARCHAR(128) NOT NULL UNIQUE,
  password VARCHAR(256) NOT NULL
);
CREATE TABLE permissions (
  id BIGSERIAL PRIMARY KEY,
  permission VARCHAR(128) NOT NULL,
  userid BIGINT NOT NULL
);

ALTER TABLE IF EXISTS permissions
    ADD CONSTRAINT fk_perm_user_id_id FOREIGN KEY (userid)
    REFERENCES users (id)
	ON UPDATE NO ACTION
    ON DELETE CASCADE;

INSERT INTO users (email, password) VALUES ('admin1', '$2a$12$LI2ggz3A85Zr7THlKjQIqOFdgNgRxHPNjy7PR9bQg9s7Ei4O9V9Ya');
INSERT INTO users (email, password) VALUES ('admin2', '$2a$12$LI2ggz3A85Zr7THlKjQIqOFdgNgRxHPNjy7PR9bQg9s7Ei4O9V9Ya');
INSERT INTO users (email, password) VALUES ('user', '$2a$12$LI2ggz3A85Zr7THlKjQIqOFdgNgRxHPNjy7PR9bQg9s7Ei4O9V9Ya');

INSERT INTO permissions (permission, userid) VALUES ('VIEW_INFO', '1');
INSERT INTO permissions (permission, userid) VALUES ('VIEW_ADMIN', '2');
INSERT INTO permissions (permission, userid) VALUES ('STANDARD', '3');