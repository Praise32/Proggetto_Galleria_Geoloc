CREATE OR REPLACE VIEW ClassificaLuoghi AS
SELECT latitudine, longitudine, nome, categoria, COUNT(id_foto) AS NumeroFotografie
FROM luogo NATURAL LEFT JOIN fotografia
GROUP BY latitudine, longitudine, nome, categoria
ORDER BY NumeroFotografie DESC, nome ASC
LIMIT 3;

CREATE OR REPLACE VIEW FotoLuogo AS
SELECT fotografia.id_foto, fotografia.titolo,
fotografia.dati_foto, fotografia.latitudine, fotografia.longitudine
luogo.nome, luogo.categoria
FROM fotografia
INNER JOIN luogo ON fotografia.latitudine = luogo.latitudine AND 
fotografia.longitudine = luogo.longitudine;
