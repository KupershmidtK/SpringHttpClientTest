-- creating tables
CREATE TABLE platform (
	id SERIAL,
	name VARCHAR
);

CREATE TABLE advertisement (
    id SERIAL,
    platform_id INTEGER,
    number VARCHAR,
    title VARCHAR,
    description VARCHAR,
    url VARCHAR,
    fraud INTEGER
);
ALTER TABLE advertisement ADD CONSTRAINT unique_number UNIQUE (platform_id, number);

-- fill tables with data
INSERT INTO platform (name)
VALUES ('AVITO'), ('VK'), ('ОДНОКЛАССНИКИ');

-- creating role with privileges
CREATE ROLE adv_service;
GRANT USAGE ON SCHEMA public TO adv_service;
GRANT ALL ON ALL TABLES IN SCHEMA public TO adv_service;
GRANT ALL ON ALL SEQUENCES IN SCHEMA public TO adv_service;

-- creating users
CREATE USER user1 WITH PASSWORD 'password' IN ROLE adv_service;
CREATE USER user2 WITH PASSWORD 'password' IN ROLE adv_service;
CREATE USER user3 WITH PASSWORD 'password' IN ROLE adv_service;
