--liquibase formatted sql

--changeset dvasev:1
CREATE TABLE IF NOT EXISTS public.type(
    id serial PRIMARY KEY NOT NULL,
    code varchar NOT NULL UNIQUE,
    label varchar NOT NULL UNIQUE
);
--rollback DROP TABLE IF EXISTS public.type;