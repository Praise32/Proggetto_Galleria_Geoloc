INSERT INTO utente (username, password, admin) VALUES
                       ('ggsolaire', 'password', false),
                       ('Cippolean', 'AOO', default),
                       ('Genny', 'IAmVengeance', true);
                       
INSERT INTO luogo (latitudine, longitudine, nome, categoria) VALUES 
                    (12.3375, 45.4341, 'Piazza San Marco', 'Piazza')
                    (12.4924, 41.8902, 'Colosseo', 'Monumento')
                    (11.2549, 43.7764, 'Biblioteca Nazionale Centrale di Firenze', 'Biblioteca')
;

INSERT INTO fotografia (username_proprietario, titolo, dati_foto, dispositivo, condivisa, posizione)
VALUES ('ggsolaire', 'Festa in giardino', '0x454F46...', 'iPhone X', false, 'Null Island');

INSERT INTO fotografia (username_proprietario, titolo, dati_foto, condivisa, posizione)
VALUES ('Cippolean', 'Panorama', '0x54875A...', true, 'Piazza San Marco');

INSERT INTO fotografia (username_proprietario, titolo, dati_foto, dispositivo, condivisa)
VALUES ('Genny', 'Il mio cane', '0x897EBA...', 'Samsung Galaxy', true);

INSERT INTO fotografia (username_proprietario, titolo, dati_foto, dispositivo, condivisa)
VALUES ('Cippolean', 'Vacanza estiva', '0xA23C5F...', 'Canon EOS', false);
