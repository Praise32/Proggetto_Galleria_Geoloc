---------------------------------------------------------------------------------------------------------------------------------------------
-- Trigger che impedisce ad un'utente di inserire all'interno di una Galleria delle
-- Fotografie  private se non è il proprietario della collezione e l'autore della foto privata
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
--Trigger che impedisce ad un'utente che di utilizzare foto private nei video
---------------------------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION controllo_privacy_video() RETURNS TRIGGER AS $$
DECLARE
proprietario_foto VARCHAR;
proprietario_video VARCHAR;
BEGIN
  IF (SELECT condivisa FROM fotografia WHERE NEW.id_foto = fotografia.id_foto) = false THEN
    RAISE EXCEPTION 'Non è possibile utilizzare foto private per video.';
  END IF;
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

	CREATE TRIGGER inserimento_frame
	BEFORE INSERT ON frame
	FOR EACH ROW
	EXECUTE FUNCTION controllo_privacy_video();

---------------------------------------------------------------------------------------------------------------------------------------------
--Trigger che elimina frame nei video dopo che una foto è stata resa privata
---------------------------------------------------------------------------------------------------------------------------------------------


CREATE OR REPLACE FUNCTION rimuovi_foto_privata() RETURNS TRIGGER AS $$
BEGIN
	DELETE FROM frame WHERE id_foto = OLD.id_foto;
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER rimuovi_foto_privata_dopo_update
AFTER UPDATE OF condivisa ON fotografia
FOR EACH ROW WHEN (NEW.condivisa = false)
EXECUTE PROCEDURE rimuovi_foto_privata();
