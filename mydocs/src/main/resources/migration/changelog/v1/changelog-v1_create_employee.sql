--liquibase formatted sql

--changeset dvasev:4
CREATE TABLE IF NOT EXISTS public.employee(
    id serial PRIMARY KEY NOT NULL,
    created_at timestamp without time zone,
    created_by varchar,
    modified_at timestamp without time zone,
    modified_by varchar,
    login varchar NOT NULL UNIQUE,
    password varchar NOT NULL,
    initials varchar NOT NULL
);
CREATE UNIQUE INDEX IF NOT EXISTS IDX_employee_login
    ON public.employee USING btree(login);
--rollback DROP TABLE IF EXISTS public.employee;
--rollback DROP INDEX IF EXISTS IDX_employee_login;