---------------------------------------------------------------------------------------------------------------------------------------------
--Trigger che aggiorna gli elementi di una galleria
---------------------------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION aggiorna_elementi_galleria() RETURNS TRIGGER AS $$
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
AFTER INSERT OR DELETE ON contenuto
FOR EACH ROW
EXECUTE FUNCTION aggiorna_elementi_galleria();


---------------------------------------------------------------------------------------------------------------------------------------------
-- Trigger per aggiornare automaticamente il valore dell'attributo "numero_frames"
-- quando si inserisce o si elimina un frame nella tabella "frame":
---------------------------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION update_video_frame_count() RETURNS TRIGGER AS $$
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
AFTER INSERT OR DELETE ON frame
FOR EACH ROW
EXECUTE FUNCTION update_video_frame_count();


---------------------------------------------------------------------------------------------------------------------------------------------
-- Trigger per generare automaticamente il valore dell'attributo "ordine"
-- quando si inserisce un nuovo frame nella tabella "frame":
---------------------------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION generate_frame_order() RETURNS TRIGGER AS $$
BEGIN
    NEW.ordine := (SELECT COALESCE(MAX(ordine), 0) FROM frame WHERE id_video = NEW.id_video) + 1;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER frame_order_trigger
BEFORE INSERT ON frame
FOR EACH ROW
EXECUTE FUNCTION generate_frame_order();




