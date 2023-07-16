---------------------------------------------------------------------------------------------------------------------------------------------
--Classifica dei top 3 luoghi più immortalati.
---------------------------------------------------------------------------------------------------------------------------------------------
CREATE OR REPLACE VIEW ClassificaLuoghi AS
SELECT latitudine, longitudine, nome, descrizione, COUNT(id_foto) AS NumeroFotografie
FROM luogo NATURAL LEFT JOIN fotografia
GROUP BY latitudine, longitudine, nome, descrizione
ORDER BY NumeroFotografie DESC, nome ASC
LIMIT 3;

--View per mostrare gli utenti dell'applicativo
CREATE OR REPLACE VIEW ShowUser AS
SELECT DISTINCT username
FROM utente;

--View per mostrare gli admin dell'applicativo
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



---------------------------------------------------------------------------------------------------------------------------------------------
--Funzioni per mostrare la galleria personale di un utente
-- "Ogni utente ha sempre la possibilità di vedere la propria personale galleria fotografica, che comprende esclusivamente le foto scattate da lui."
---------------------------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION GalleriaUtente (Utente utente.username%TYPE, Richiedente utente.username%TYPE) RETURNS SETOF fotografia AS
$$
BEGIN
    RETURN QUERY (
        SELECT * FROM fotografia
        WHERE fotografia.username_autore = Utente AND (
            fotografia.username_autore = Richiedente OR fotografia.condivisa = TRUE
        )
    );
END;
$$ LANGUAGE plpgsql;


--Collezioni di un utente
CREATE OR REPLACE FUNCTION CollezioniUtente(utente utente.username%TYPE) RETURNS SETOF collezione AS
$$
BEGIN
    RETURN QUERY (
        SELECT * FROM collezione
        WHERE collezione.username = utente
    );
END;
$$LANGUAGE plpgsql;

--Contenuto di una Collezione condivisa
CREATE OR REPLACE FUNCTION ContenutoCollezione (collezione collezione.id_collezione%TYPE) RETURNS SETOF fotografia AS
$$
BEGIN
    RETURN QUERY(
        SELECT fotografia.*
        FROM contenuto NATURAL JOIN fotografia
        WHERE id_collezione = collezione
    );
END;
$$ LANGUAGE plpgsql;

---------------------------------------------------------------------------------------------------------------------------------------------
--Recupero di tutte le fotografie che sono state scattate nello stesso luogo;
---------------------------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION foto_per_luogo(nome luogo.nome%TYPE, utente fotografia.username_autore%TYPE)
RETURNS SETOF fotografia AS
$$
BEGIN
    RETURN QUERY (
        SELECT *
        FROM fotografia
        JOIN luogo ON fotografia.latitudine = luogo.latitudine AND fotografia.longitudine = luogo.longitudine
        WHERE luogo.nome = nome AND (
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



--view per visualizzare un video come un insieme di frame
CREATE OR REPLACE FUNCTION visualizza_video(in_titolo video.titolo%TYPE)
RETURNS SETOF frame AS
$$
BEGIN
    RETURN QUERY (
        SELECT frame.*
        FROM frame
        JOIN video ON video.id_video = frame.id_video
        WHERE video.titolo = in_titolo
		ORDER BY ordine
    );
END;
$$ LANGUAGE plpgsql;
