CREATE TABLE districts
(
    id   BIGINT NOT NULL,
    name VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE regions
(
    id          BIGINT NOT NULL,
    name        VARCHAR(255),
    district_id BIGINT,
    PRIMARY KEY (id)
);
ALTER TABLE regions
    ADD CONSTRAINT fk_regions_on_district_id FOREIGN KEY (district_id) REFERENCES districts (id) ON DELETE RESTRICT;
CREATE INDEX idx_regions_on_district_id ON regions (district_id);

CREATE TABLE cities
(
    id        BIGINT NOT NULL,
    name      VARCHAR(255),
    region_id BIGINT,
    PRIMARY KEY (id)
);
ALTER TABLE cities
    ADD CONSTRAINT fk_cities_on_region_id FOREIGN KEY (region_id) REFERENCES regions (id) ON DELETE RESTRICT;
CREATE INDEX idx_cities_on_region_id ON cities (region_id);

CREATE SEQUENCE users_seq START 1 INCREMENT BY 50;
CREATE TABLE users
(
    id                   BIGINT NOT NULL,
    first_name           VARCHAR(255),
    last_name            VARCHAR(255),
    gender               VARCHAR(255),
    age                  INT,
    free_link            VARCHAR(255),
    own_link             VARCHAR(255),
    about_me             VARCHAR(255),
    date_of_registration TIMESTAMP WITH TIME ZONE,
    city_of_residence_id BIGINT,
    telegram             VARCHAR(255) UNIQUE,
    role                 VARCHAR(255),
    PRIMARY KEY (id)
);
ALTER TABLE users
    ADD CONSTRAINT fk_users_on_city_of_residence_id FOREIGN KEY (city_of_residence_id) REFERENCES cities (id) ON DELETE SET NULL;

CREATE SEQUENCE ads_seq START 1 INCREMENT BY 50;
CREATE TABLE ads
(
    id            BIGINT NOT NULL,
    title         VARCHAR(255),
    type          VARCHAR(255),
    user_id       BIGINT,
    free_link     VARCHAR(255),
    own_link      VARCHAR(255),
    contacts      VARCHAR(255),
    details       VARCHAR(255),
    visibility    VARCHAR(255),
    creation_date TIMESTAMP WITH TIME ZONE,
    PRIMARY KEY (id)
);
ALTER TABLE ads
    ADD CONSTRAINT fk_ads_on_user_id FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE;

CREATE SEQUENCE tags_seq START 1 INCREMENT BY 50;
CREATE TABLE tags
(
    id   BIGINT NOT NULL,
    name VARCHAR(255),
    type VARCHAR(255),
    PRIMARY KEY (id)
);
ALTER TABLE tags
    ADD CONSTRAINT uc_tags_name_type UNIQUE (name, type);

CREATE SEQUENCE ad_competence_level_tags_seq START 1 INCREMENT BY 50;
CREATE TABLE ad_competence_level_tags
(
    id               BIGINT NOT NULL,
    competence_level INT,
    ad_id            BIGINT,
    tag_id           BIGINT,
    PRIMARY KEY (id)
);
ALTER TABLE ad_competence_level_tags
    ADD CONSTRAINT fk_ad_competence_level_tags_on_ad_id FOREIGN KEY (ad_id) REFERENCES ads (id) ON DELETE CASCADE;
ALTER TABLE ad_competence_level_tags
    ADD CONSTRAINT fk_ad_competence_level_tags_on_tag_id FOREIGN KEY (tag_id) REFERENCES tags (id) ON DELETE CASCADE;
