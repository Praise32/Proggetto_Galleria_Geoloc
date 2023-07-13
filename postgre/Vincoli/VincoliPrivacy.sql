---------------------------------------------------------------------------------------------------------------------------------------------
-- Trigger che impedisce ad un'utente di inserire all'interno di una Galleria delle
-- Fotografie  private se non è l'autore di esse 
---------------------------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION controllo_autore() RETURNS TRIGGER AS $$
DECLARE
proprietario_foto VARCHAR;
proprietario_collezione VARCHAR;
BEGIN
	SELECT username_autore INTO proprietario_foto FROM fotografia WHERE id_foto=NEW.id_foto;
	SELECT username INTO proprietario_collezione FROM collezione WHERE id_collezione=NEW.id_collezione;
    IF NOT EXISTS (SELECT * FROM fotografia WHERE id_foto = NEW.id_foto AND ((condivisa = true) OR (proprietario_foto = proprietario_collezione))) THEN
        RAISE EXCEPTION 'Non sei autorizzato ad utilizzare questa foto';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


	CREATE TRIGGER inserimento_Galleria
	BEFORE INSERT ON Contenuto
	FOR EACH ROW
	EXECUTE FUNCTION controllo_autore();


---------------------------------------------------------------------------------------------------------------------------------------------
--Trigger che impedisce ad un'utente che di utilizzare foto private se non ne è l'autore nei video
---------------------------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION controllo_autore_video() RETURNS TRIGGER AS $$
DECLARE
proprietario_foto VARCHAR;
proprietario_video VARCHAR;
BEGIN
	SELECT username_autore INTO proprietario_foto FROM fotografia WHERE id_foto=NEW.id_foto;
	SELECT autore INTO proprietario_video FROM video WHERE id_video=NEW.id_video;
    IF NOT EXISTS (SELECT * FROM fotografia WHERE id_foto = NEW.id_foto AND ((condivisa = true) OR (proprietario_foto = proprietario_video))) THEN
        RAISE EXCEPTION 'Non sei autorizzato ad utilizzare questa foto';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

	CREATE TRIGGER inserimento_frame
	BEFORE INSERT ON frame
	FOR EACH ROW
	EXECUTE FUNCTION controllo_autore_video();

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




