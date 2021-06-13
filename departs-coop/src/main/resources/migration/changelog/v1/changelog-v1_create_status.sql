--liquibase formatted sql

--changeset dvasev:3
CREATE TABLE IF NOT EXISTS public.status(
    id serial PRIMARY KEY NOT NULL,
    code varchar NOT NULL UNIQUE,
    label varchar NOT NULL UNIQUE
);
--rollback DROP TABLE IF EXISTS public.status;