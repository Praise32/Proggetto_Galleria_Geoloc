CREATE OR REPLACE VIEW foto_per_luogo AS
SELECT *
FROM fotografia
WHERE latitudine = $1 AND longitudine = $2;

CREATE OR REPLACE VIEW ClassificaLuoghi AS
SELECT latitudine, longitudine, nome, categoria, COUNT(id_foto) AS NumeroFotografie
FROM luogo NATURAL LEFT JOIN fotografia
GROUP BY latitudine, longitudine, nome, categoria
ORDER BY NumeroFotografie DESC, nome ASC
LIMIT 3;

CREATE OR REPLACE VIEW ListaUtenti AS
SELECT DISTINCT username
FROM utente;

CREATE OR REPLACE VIEW ShowAdmin AS
SELECT DISTINCT username, admin
FROM utente
WHERE admin = true;
