---------------------------------------------------------------------------------------------------------------------------------------------
--Trigger che aggiorna gli elementi di una galleria
---------------------------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION aggiorna_elementi_galleria() RETURNS TRIGGER AS
$$
BEGIN
    IF (TG_OP = 'INSERT') THEN
        UPDATE collezione SET numero_elementi = numero_elementi + 1 WHERE id_collezione = NEW.id_collezione;
    ELSIF (TG_OP = 'DELETE') THEN
        UPDATE collezione SET numero_elementi = numero_elementi - 1 WHERE id_collezione = OLD.id_collezione;
    END IF;
    RETURN NULL;
END;
$$ LANGUAGE PLPGSQL;

CREATE TRIGGER aggiorna_elementi_galleria_trigger
    AFTER INSERT OR DELETE
    ON contenuto
    FOR EACH ROW
EXECUTE FUNCTION aggiorna_elementi_galleria();


---------------------------------------------------------------------------------------------------------------------------------------------
-- Trigger per aggiornare automaticamente il valore dell'attributo "numero_frames"
-- quando si inserisce o si elimina un frame nella tabella "frame":
---------------------------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION update_video_frame_count() RETURNS TRIGGER AS
$$
BEGIN
    IF (TG_OP = 'INSERT') THEN
        UPDATE video SET numero_frames = numero_frames + 1 WHERE id_video = NEW.id_video;
    ELSIF (TG_OP = 'DELETE') THEN
        UPDATE video SET numero_frames = numero_frames - 1 WHERE id_video = OLD.id_video;
    END IF;
    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_video_frame_count_trigger
    AFTER INSERT OR DELETE
    ON frame
    FOR EACH ROW
EXECUTE FUNCTION update_video_frame_count();


---------------------------------------------------------------------------------------------------------------------------------------------
-- Trigger per generare automaticamente il valore dell'attributo "ordine"
-- quando si inserisce un nuovo frame nella tabella "frame":
---------------------------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION generate_frame_order() RETURNS TRIGGER AS
$$
BEGIN
    NEW.ordine := (SELECT COALESCE(MAX(ordine), 0) FROM frame WHERE id_video = NEW.id_video) + 1;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER frame_order_trigger
    BEFORE INSERT
    ON frame
    FOR EACH ROW
EXECUTE FUNCTION generate_frame_order();

---------------------------------------------------------------------------------------------------------------------------------------------
-- Trigger per aggiorare automaticamente il valore dell' attributo "durata" nella tabella "video"
-- quando si inserisce un nuovo frame nella tabella "frame" oppure si modifica la durata di un frame:
---------------------------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION update_video_duration() RETURNS TRIGGER AS
$$
BEGIN
    UPDATE video
    SET durata = (SELECT SUM(durata)
                  FROM frame
                  WHERE id_video = NEW.id_video)
    WHERE id_video = NEW.id_video;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_video_duration
    AFTER INSERT OR UPDATE
    ON frame
    FOR EACH ROW
EXECUTE FUNCTION update_video_duration();


---------------------------------------------------------------------------------------------------------------------------------------------
-- Trigger che riordina i frame all'interno di un video dopo l'eliminazione di un frame
---------------------------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION riordina_frame() RETURNS TRIGGER AS
$$
BEGIN
    -- Riduci l'ordine dei frame successivi a quello eliminato
    UPDATE frame
    SET ordine = ordine - 1
    WHERE id_video = OLD.id_video
      AND ordine > OLD.ordine;

    RETURN NULL; -- il valore di ritorno non è rilevante per un trigger AFTER DELETE
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER riordina_frame_trigger
    AFTER DELETE
    ON frame
    FOR EACH ROW
EXECUTE FUNCTION riordina_frame();



---------------------------------------------------------------------------------------------------------------------------------------------
-- Trigger che tronca i decimali fino alla quarta cifra quando si riceve un insert di fotografia da Java
-- Per via della conversione da Java ad SQL, il valore delle coordinate prende molte più cifre decimali, violando la FK
---------------------------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION tronca_coordinate()
    RETURNS TRIGGER AS
$$
BEGIN
    NEW.latitudine = round(NEW.latitudine::numeric, 4);
    NEW.longitudine = round(NEW.longitudine::numeric, 4);

    INSERT INTO Fotografia VALUES (NEW.id_foto, NEW.username_autore, NEW.dati_foto, NEW.dispositivo, NEW.data_foto, NEW.latitudine, NEW.longitudine, NEW.condivisa, NEW.titolo);

    -- Indicare che l'operazione di inserimento è stata gestita con successo
    RETURN NEW; -- Poiché stai gestendo l'inserimento manualmente, restituisci NULL

    END;
$$ LANGUAGE plpgsql;


-- Il problema sta nel fatto che questo trigger viene avviato quando avviene l'insert all'interno della funzione
CREATE OR REPLACE TRIGGER tronca_coordinate_trigger
    BEFORE INSERT
    ON fotografia
    FOR EACH ROW
    WHEN (pg_trigger_depth() = 0)
EXECUTE FUNCTION tronca_coordinate();


