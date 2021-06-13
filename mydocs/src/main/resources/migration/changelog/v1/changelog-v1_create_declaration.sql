--liquibase formatted sql

--changeset dvasev:5
CREATE TABLE IF NOT EXISTS public.declaration(
    id serial PRIMARY KEY NOT NULL,
    created_at timestamp without time zone,
    created_by varchar,
    modified_at timestamp without time zone,
    modified_by varchar,
    number varchar NOT NULL UNIQUE,
    client_initials varchar NOT NULL,
    passport varchar NOT NULL,
    employee_id bigint NOT NULL,
    type_id bigint NOT NULL,
    department_id bigint NOT NULL,
    status_id bigint NOT NULL,
    CONSTRAINT fk_employee FOREIGN KEY(employee_id) REFERENCES public.employee(id),
    CONSTRAINT fk_type FOREIGN KEY(type_id)  REFERENCES public.type(id),
    CONSTRAINT fk_department FOREIGN KEY(department_id) REFERENCES public.department(id),
    CONSTRAINT fk_status FOREIGN KEY(status_id) REFERENCES public.status(id)
);
CREATE UNIQUE INDEX IF NOT EXISTS IDX_declaration_number
    ON public.declaration USING btree(number);
CREATE INDEX IF NOT EXISTS IDX_declaration_passport
    ON public.declaration USING btree(passport);
--rollback DROP TABLE IF EXISTS public.declaration;
--rollback DROP INDEX IF EXISTS IDX_declaration_number;
--rollback DROP INDEX IF EXISTS IDX_declaration_passport;