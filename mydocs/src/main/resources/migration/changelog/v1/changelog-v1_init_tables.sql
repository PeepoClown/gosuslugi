--liquibase formatted sql

--changeset dvasev:6
INSERT INTO public.type (code, label) VALUES ('DOC-PFR', 'Заявление в ПФР');
INSERT INTO public.type (code, label) VALUES ('DOC-FNS', 'Заявление в ФНС');

--changeset dvasev:7
INSERT INTO public.department (code, label) VALUES ('DEP-PFR', 'Ведомство ПФР');
INSERT INTO public.department (code, label) VALUES ('DEP-FNS', 'Ведомство ФНС');

--changeset dvasev:8
INSERT INTO public.status (code, label) VALUES ('NEW', 'Заявление зарегистрировано');
INSERT INTO public.status (code, label) VALUES ('PROCESSING', 'Заявление было передано в ведомство');
INSERT INTO public.status (code, label) VALUES ('COMPLETED', 'Заявление было обработано ведомством');

--changeset dvasev:9
INSERT INTO public.employee (created_at, created_by, modified_at, modified_by, login, password, initials)
VALUES (CURRENT_TIMESTAMP, 'anonymousUser', CURRENT_TIMESTAMP, 'anonymousUser', 'employeeAdmin',
        '$2a$10$EldMMPar3L7gdseUKUZ2J.BGN74AEWLg2cHd9kbNdBNS.GU.FHDii', 'Романов Константин Алексеевич');
INSERT INTO public.employee (created_at, created_by, modified_at, modified_by, login, password, initials)
VALUES (CURRENT_TIMESTAMP, 'anonymousUser', CURRENT_TIMESTAMP, 'anonymousUser', 'bot',
        '$2y$12$XSrOk3GxgWF2qb5daWljzOyMVfEjP4H/8BNHmFbIZLf2siSeq1HwC', 'none');