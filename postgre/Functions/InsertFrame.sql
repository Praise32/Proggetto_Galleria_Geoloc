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

CREATE OR REPLACE FUNCTION fotografia_per_soggetto(nome_soggetto tag_soggetto.nome_soggetto%TYPE)
RETURNS SETOF fotografia AS
$$
BEGIN
    RETURN QUERY(
        SELECT fotografia.*
        FROM fotografia
        JOIN tag_soggetto ON fotografia.id_foto = tag_soggetto.id_foto
        WHERE tag_soggetto.nome_soggetto = nome_soggetto
    );
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION FotoLuogo(latitudine luogo.latitudine%TYPE, longitudine foto.longitudine%TYPE, utente utente.username%TYPE) RETURNS SETOF fotografia AS
$$
BEGIN
    RETURN QUERY (
        SELECT *
        FROM fotografia
        WHERE fotografia.latitudine = latitudine AND 
        fotografia.longitudine = longitudine AND (
            fotografia.username_proprietario = utente OR fotografia.condivisa = TRUE
        )
    );
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION insert_frame_in_video(input_id_video video.id_video%TYPE, input_ordine frame.ordine%TYPE)
