INSERT INTO polaznik (ime, prezime)
VALUES
    ('Ivan', 'Ivic'),
    ('Ana', 'Anic'),
    ('Marko', 'Markic');

INSERT INTO program_obrazovanja (naziv, csvet)
VALUES
    ('Programiranje u Javi', 10),
    ('Web programiranje', 8),
    ('Baze podataka', 6);

INSERT INTO upis (id_polaznik, id_program_obrazovanja)
VALUES
    (1, 1),
    (2, 2),
    (3, 3);


INSERT INTO users(username, password)
VALUES
    ('user', '$2a$12$h0HcS2QDb/7zPASbLa2GoOTSRP6CWK0oX7pCK.dPjkM6L5N4pNovi'),
    ('admin', '$2a$12$INo0nbj40sQrTB7b28KJput/bNltGmFyCfRsUhvy73qcXo5/XdsTG');


INSERT INTO authority (authority_name)
VALUES
    ('ROLE_ADMIN'),
    ('ROLE_USER');

INSERT INTO users_authority (user_id, authority_id)
VALUES
    (1, 2),
    (2, 1);
