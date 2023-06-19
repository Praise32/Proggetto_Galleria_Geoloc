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


CREATE OR REPLACE FUNCTION CollezioniUtente(utente utente.username%TYPE) RETURNS SETOF collezione AS
$$
BEGIN
    RETURN QUERY (
        SELECT * FROM collezione
        WHERE collezione.username = utente
    );
END;
$$LANGUAGE plpgsql;


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


CREATE OR REPLACE FUNCTION foto_per_soggetto(in_nome_soggetto soggetto.nome%TYPE, utente_foto fotografia.username_autore%TYPE)
RETURNS SETOF fotografia AS
$$
BEGIN
    RETURN QUERY (
    SELECT fotografia.id_foto, fotografia.username_autore, fotografia.titolo, fotografia.dati_foto,
    fotografia.dispositivo, fotografia.latitudine, fotografia.longitudine, fotografia.condivisa, soggetto.nome
    FROM fotografia f JOIN tag_soggetto t ON f.id_foto = t.id_foto JOIN soggetto s ON t.nome_soggetto = s.nome
    WHERE s.nome = in_nome_soggetto AND (
            fotografia.username_autore = utente_foto OR fotografia.condivisa = TRUE
        )
    );
END;
$$
LANGUAGE PLPGSQL;


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


CREATE OR REPLACE FUNCTION foto_per_luogo(in_nome luogo.nome%TYPE, utente fotografia.username_autore%TYPE)
RETURNS SETOF fotografia AS
$$
BEGIN
    RETURN QUERY (
        SELECT *
        FROM fotografia
        JOIN luogo ON fotografia.latitudine = luogo.latitudine AND fotografia.longitudine = luogo.longitudine
        WHERE luogo.nome = in_nome AND (
            fotografia.username_autore = utente OR fotografia.condivisa = TRUE
        )
    );
END;
$$ LANGUAGE plpgsql;


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
