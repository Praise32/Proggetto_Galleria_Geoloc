---------------------------------------------------------------------------------------------------------------------------------------------
--TUTTE QUESTE QUERY SONO MESSE ANCHE NELLA CARTELLA VIEWS PERCHE' PENSO NELLA RELAZIONE SIA GIUSTO METTERLE LI'
---------------------------------------------------------------------------------------------------------------------------------------------







---------------------------------------------------------------------------------------------------------------------------------------------
--Classifica dei top 3 luoghi più immortalati.
---------------------------------------------------------------------------------------------------------------------------------------------
CREATE OR REPLACE VIEW ClassificaLuoghi AS
SELECT latitudine, longitudine, nome, descrizione, COUNT(id_foto) AS NumeroFotografie
FROM luogo NATURAL LEFT JOIN fotografia
GROUP BY latitudine, longitudine, nome, descrizione
ORDER BY NumeroFotografie DESC, nome ASC
LIMIT 3;


---------------------------------------------------------------------------------------------------------------------------------------------
--Recupero di tutte le fotografie che sono state scattate nello stesso luogo
---------------------------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION foto_per_luogo(in_nome luogo.nome%TYPE, utente fotografia.username_autore%TYPE)
RETURNS SETOF fotografia AS
$$
BEGIN
    RETURN QUERY (
        SELECT fotografia.*
        FROM fotografia
        JOIN luogo ON fotografia.latitudine = luogo.latitudine AND fotografia.longitudine = luogo.longitudine
        WHERE luogo.nome = in_nome AND (
            fotografia.username_autore = utente OR fotografia.condivisa = TRUE
        )
    );
END;
$$ LANGUAGE plpgsql;


---------------------------------------------------------------------------------------------------------------------------------------------
--Recupero di tutte le fotografie che condividono lo stesso utente come soggetto
---------------------------------------------------------------------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION foto_per_tag_utente(in_username tag_utente.username%TYPE)
RETURNS SETOF fotografia AS
$$
BEGIN
    RETURN QUERY (
        SELECT fotografia.*
        FROM fotografia
        JOIN tag_utente ON tag_utente.id_foto = fotografia.id_foto
        WHERE tag_utente.username = in_username
    );
END;
$$ LANGUAGE plpgsql;


---------------------------------------------------------------------------------------------------------------------------------------------
--Recupero di tutte le fotografie che condividono lo stesso soggetto;
---------------------------------------------------------------------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION foto_per_tag_soggetto(in_nome_soggetto tag_soggetto.nome_soggetto%TYPE, utente_richiedente fotografia.username_autore%TYPE)
RETURNS SETOF fotografia AS
$$
BEGIN
    RETURN QUERY (
        SELECT fotografia.*
        FROM fotografia
        JOIN tag_soggetto ON tag_soggetto.id_foto = fotografia.id_foto
        WHERE tag_soggetto.nome_soggetto = in_nome_soggetto AND (
			fotografia.username_autore= utente_richiedente OR fotografia.condivisa = TRUE)
    );
END;
$$ LANGUAGE plpgsql;
