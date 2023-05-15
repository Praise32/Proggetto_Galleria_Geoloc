CREATE OR REPLACE FUNCTION GalleriaUtente (Proprietario utente.username%TYPE, Utente utente.username%TYPE) RETURNS SETOF fotografia AS
$$
BEGIN
    RETURN QUERY (
        SELECT * FROM fotografia
        WHERE fotografia.username_autore = Proprietario AND (
            fotografia.username_autore = utente OR fotografia.condivisa = TRUE
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

CREATE OR REPLACE VIEW ClassificaLuoghi AS
        SELECT latitudine, longitudine, nome, categoria, COUNT(id_foto) AS NumeroFotografie
        FROM luogo NATURAL LEFT JOIN fotografia
        GROUP BY latitudine, longitudine, nome, categoria
        ORDER BY NumeroFotografie DESC, nome ASC
        LIMIT 3;












CREATE OR REPLACE FUNCTION insert_frame_in_video(input_id_video video.id_video%TYPE, input_ordine frame.ordine%TYPE)
