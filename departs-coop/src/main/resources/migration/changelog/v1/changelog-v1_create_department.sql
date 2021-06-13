--liquibase formatted sql

--changeset dvasev:2
CREATE TABLE IF NOT EXISTS public.department(
    id serial PRIMARY KEY NOT NULL,
    code varchar NOT NULL UNIQUE,
    label varchar NOT NULL UNIQUE
);
--rollback DROP TABLE IF EXISTS public.department;