--Inserimento degli utenti
INSERT INTO utente (username, password, admin) VALUES
  ('gennaro', 'password1', true),
  ('luigi', 'password2', false),
  ('mariano', 'password3', false);

--Inserimento dei luoghi
INSERT INTO luogo (latitudine, longitudine, nome, descrizione) VALUES
  (41.9028, 12.4964, 'Colosseo', 'Anfiteatro Flavio'),
  (41.8644, 12.7497, 'Bar Callisto', 'Bar storico di Trastevere'),
  (41.8919, 12.5113, 'Fontana di Trevi','Fontana Barocca'),
  (41.9060, 12.4534, 'Villa Borghese', 'Il Pincio'),
  (41.7468, 12.1548, 'Giardino degli aranci', 'Parco Savello'),
  (40.3759, 11.8538, 'Tour Eiffel', 'Torre autoportante di ferro battuto'),
  (40.3942, 11.5977, 'Museo dOrsay', 'Museo Impressionismo e Post-Impressionismo'),
  (40.3234, 11.7647, 'Museo del Louvre', 'Tra i musei più famosi al mondo'),
  (40.2342, 11.9879, 'Arco Di Trionfo', 'Simbolo architettonico di una vittoria'),
  (60.3453, 18.1928, 'Cortina dAmpezzo', 'Località Sciistica'),
  (60.3653, 18.1238, 'Passo di Falzarego', 'Zona pista sci'), 
  (60.3656, 18.7538, 'Stadio Olimpico del Ghiaccio', 'Stadio Olimpico di Sci'),
  (60.7653, 18.3338, ' Tofane', 'Snowpark'),
  (40.3423, 10.9835, 'Reggia di caserta', 'Monumento'),
  (40.3663, 10.9135, 'Flora', 'Luogo di ritrovo'),
  (40.3663, 10.9634, 'Comune di caserta', 'Palazzo comunale'),
  (40.3934, 10.9217, 'Via Vico', 'Via trafficata'),
  (40.3016, 10.9982, 'MCDonald di caserta', 'Ristorante'),
  (40.3486, 10.9779, 'Pizza Napoli', 'Pizzeria'),
  (40.398, 10.9799, 'Via Ferrante', 'Via trafficata');


--Inserimento delle fotografie
INSERT INTO fotografia (username_autore, dati_foto, dispositivo, data_foto, latitudine, longitudine, condivisa, titolo) VALUES
  ('gennaro', '01010101', 'iPhone', '2022-06-01 10:00:00', 41.9028, 12.4964, true, 'colosseo.jpg'),
  ('gennaro', '10101010', 'iPhone', '2022-06-02 11:00:00', 41.8919, 12.5113, true, 'fontana.jpg'),
  ('gennaro', '00110011', 'iPhone', '2022-06-03 12:00:00', 41.9060, 12.4534, true, 'villaborghese.jpg'),
  ('gennaro', '11010010', 'iPhone', '2022-06-02 18:00:00', 41.7468, 12.1548, false, 'Giardino.jpg'),
  ('gennaro', '01001101', 'iPhone', '2022-06-03 16:00:00', 41.8644, 12.7497, false, 'barcallisto.jpg'),
  ('luigi','00101101', 'Samsung', '2023-01-20 10:30:00', 40.3759, 11.8538, true, 'eiffel.jpg'),
  ('luigi','00111101', 'Samsung', '2023-01-20 12:30:00', 40.3942, 11.5977 ,false, 'dorsay.jpg'),
  ('luigi','00101001', 'Samsung', '2023-01-21 10:00:00', 40.3234, 11.7647 ,false, 'louvre.jpg'),
  ('luigi','10101101', 'Samsung', '2023-01-21 14:30:00', 40.2342, 11.9879, true, 'arco.jpg'),
  ('mariano','11100101', 'Samsung', '2022-12-27 12:00:00', 60.3453, 18.1928, true, 'casetta.jpg'),
  ('mariano','11100101', 'Samsung', '2022-12-27 13:00:00', 60.3653, 18.1238, true, 'falzarego.jpg'),
  ('mariano','11100101', 'Samsung', '2022-12-28 16:00:00', 60.3656, 18.7538, true, 'stadio.jpg'),
  ('mariano','11100101', 'Samsung', '2022-12-29 10:00:00', 60.7653, 18.3338, true, 'snowpark.jpg'),
  ('gennaro', '01010101', 'iPhone', '2023-06-02 01:20:00', 40.3423, 10.9835, true, 'reggia.jpg'),
  ('luigi', '01010101', 'iPhone', '2023-06-02 01:20:00', 40.3423, 10.9835, true, 'reggia.jpg'),
  ('mariano', '01110101', 'Samsung', '2023-06-02 01:20:00', 40.3423, 10.9835, true, 'reggia.jpg'),
  ('gennaro', '01110101', 'iPhone', '2023-06-01 21:20:00', 40.3663, 10.9135, true, 'flora.jpg'),
  ('gennaro', '11010101', 'iPhone', '2023-06-01 21:00:00', 40.3663, 10.9135, true, 'flora.jpg'),
  ('luigi', '01110101', 'Samsung', '2023-06-01 22:00:00', 40.3663, 10.9634, true, 'comune.jpg'),
  ('mariano', '01000101', 'Samsung', '2023-06-01 22:40:00', 40.3934, 10.9217, true, 'viavico.jpg'),
  ('mariano', '01000101', 'Samsung', '2023-06-01 23:20:00', 40.3016, 10.9982, true, 'mcdonald.jpg'),
  ('luigi', '01010001', 'Samsung', '2023-06-01 20:00:00', 40.3486, 10.9779, true, 'pizza.jpg'),
  ('gennaro', '01011101', 'iPhone', '2023-06-01 22:20:00', 40.398, 10.9799, true, 'viaferry.jpg');




--Inserimento delle collezioni
INSERT INTO collezione (id_collezione, username, titolo, data_collezione, numero_elementi) VALUES
  (1, 'gennaro', 'Vacanza a Roma', '2022-06-01 10:00:00', 5),
  (2, 'luigi', 'Viaggio a Parigi', '2023-01-20 11:00:00', 4),
  (3, 'mariano', 'Weekend in montagna', '2022-12-27 12:00:00', 4),
  (4, 'gennaro', 'sabato sera a Caserta', '2023-06-01 20:00:00', 0);
--Inserimento dei contenuti delle collezioni
INSERT INTO contenuto (id_collezione, id_foto) VALUES
  (1, 1),
  (1, 2),
  (1, 3),
  (1, 4),
  (1, 5),
  (2, 6),
  (2, 7),
  (2, 8),
  (2, 9),
  (3, 10),
  (3, 11),
  (3, 12),
  (3, 13),
  (4, 14),
  (4, 15),
  (4, 16),
  (4, 17),
  (4, 18),
  (4, 19),
  (4, 20),
  (4, 21),
  (4, 22),
  (4, 23);

/*
--Inserimento dei tag degli utenti
INSERT INTO tag_utente (username, id_foto) VALUES
  ('mariano', 14),
  ('luigi', 14),
  ('mariano', 15),
  ('luigi', 16),
  ('gennaro', 16),
  ('mariano', 17),
  ('gennaro', 17),
  ('mariano', 18),
  ('luigi', 19),
  ('gennaro', 20),
  ('mariano', 23),
  ('luigi', 23),
  ('gennaro', 23);

--Inserimento dei video
INSERT INTO video (autore, titolo, numero_frames, durata, descrizione) VALUES
  ('mario', 'Viaggio in Giappone', 100, 600, 'Video del nostro viaggio in Giappone'),
  ('giuseppe', 'Festa di compleanno', 50, 300, 'Video della festa di compleanno di mio figlio'),
  ('valentina', 'Vacanza al mare', 80, 480, 'Video della nostra vacanza al mare');

--Inserimento dei frames dei video
INSERT INTO frame (id_video, id_foto, durata, ordine) VALUES
  (1, 1, 6, 1),
  (1, 2, 8, 2),
  (1, 3, 10, 3),
  (2, 2, 6, 1),
  (2, 3, 8, 2),
  (3, 1, 6, 1),
  (3, 3, 8, 2);

--Inserimento dei soggetti
INSERT INTO soggetto (nome, categoria) VALUES
  ('Cane', 'Animale'),
  ('Gatto', 'Animale'),
  ('Montagna', 'Paesaggio');

--Inserimento dei tag dei soggetti
INSERT INTO tag_soggetto (nome_soggetto, id_foto) VALUES
  ('Cane', 1),
  ('Gatto', 2),
  ('Montagna', 3);
