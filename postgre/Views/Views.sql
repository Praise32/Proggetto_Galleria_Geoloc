
CREATE OR REPLACE VIEW ClassificaLuoghi AS
SELECT latitudine, longitudine, nome, categoria, COUNT(id_foto) AS NumeroFotografie
FROM luogo NATURAL LEFT JOIN fotografia
GROUP BY latitudine, longitudine, nome, categoria
ORDER BY NumeroFotografie DESC, nome ASC
LIMIT 3;

CREATE OR REPLACE VIEW ShowUser AS
SELECT DISTINCT username
FROM utente;

CREATE OR REPLACE VIEW ShowAdmin AS
SELECT DISTINCT username, admin
FROM utente
WHERE admin = true;

--View per visualizzare i dati di ogni frame che è utilizzato in almeno un video
CREATE OR REPLACE VIEW ContenutoFrame AS
SELECT fotografia.dati_foto, frame.*
FROM fotografia
INNER JOIN frame ON fotografia.id_foto = frame.id_foto;

--View per visualizzare una lista delle categorie dei soggetti nel database
CREATE OR REPLACE VIEW CategoriaSoggetto AS
SELECT DISTINCT soggetto.categoria
FROM soggetto;

--View per lista di tutti i video
CREATE OR REPLACE VIEW ShowVideos AS
SELECT titolo AS "Titolo", autore AS "Autore", descrizione AS "Info"
FROM video;