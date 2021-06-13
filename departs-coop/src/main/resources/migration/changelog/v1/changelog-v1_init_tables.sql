--liquibase formatted sql

--changeset dvasev:5
INSERT INTO public.type (code, label) VALUES ('DOC-PFR', 'Заявление в ПФР');
INSERT INTO public.type (code, label) VALUES ('DOC-FNS', 'Заявление в ФНС');

--changeset dvasev:6
INSERT INTO public.department (code, label) VALUES ('DEP-PFR', 'Ведомство ПФР');
INSERT INTO public.department (code, label) VALUES ('DEP-FNS', 'Ведомство ФНС');

--changeset dvasev:7
INSERT INTO public.status (code, label) VALUES ('WAIT', 'Заявление ожидает обработки');
INSERT INTO public.status (code, label) VALUES ('COMPLETED', 'Заявление было обработано ведомством');