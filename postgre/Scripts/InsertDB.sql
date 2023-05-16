INSERT INTO utente (username, password, admin) VALUES
                       ('ggsolaire', 'password', false),
                       ('Cippolean', 'AOO', default),
                       ('Genny', 'IAmVengeance', true);

INSERT INTO luogo (latitudine, longitudine, nome, categoria) VALUES
                    (0, 0, 'Null Island', 'Punto d''interesse'),
                    (12.3375, 45.4341, 'Piazza San Marco', 'Piazza'),
                    (12.4924, 41.8902, 'Colosseo', 'Monumento'),
                    (11.2549, 43.7764, 'Biblioteca Nazionale Centrale di Firenze', 'Biblioteca')
;

INSERT INTO fotografia (username_autore, titolo, dati_foto, dispositivo, condivisa, latitudine, longitudine)
VALUES ('ggsolaire', 'Festa in giardino', '0x454F46...', 'iPhone X', false, 0,0),
       ('Cippolean', 'Panorama', '0x54875A...', true, 12.3375, 45.4341);

INSERT INTO fotografia (username_autore, titolo, dati_foto, dispositivo, condivisa)
VALUES ('Genny', 'Il mio cane', '0x897EBA...', 'Samsung Galaxy', true),
       ('Cippolean', 'Vacanza estiva', '0xA23C5F...', 'Canon EOS', false);
