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
-- Trigger che impedisce ad un'utente di inserire all'interno di Collezioni o Video foto private
---------------------------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION verifica_privacy_foto() RETURNS TRIGGER AS $$
BEGIN
  IF (SELECT condivisa FROM fotografia WHERE NEW.id_foto = fotografia.id_foto) = false THEN
    RAISE EXCEPTION 'Non è possibile utilizzare foto private per collezioni o video.';
  END IF;
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER verifica_privacy_foto_prima_inserimento
BEFORE INSERT ON contenuto
FOR EACH ROW EXECUTE PROCEDURE verifica_privacy_foto();

CREATE TRIGGER verifica_privacy_foto_prima_inserimento_frame
BEFORE INSERT ON frame
FOR EACH ROW EXECUTE PROCEDURE verifica_privacy_foto();



---------------------------------------------------------------------------------------------------------------------------------------------
-- Trigger che  setta a NULL le foto in Frame, poichè sta on delete set NULL nella creazione del database e poi elimina la foto dalla collezione
-- quando passa da condivisa a privata
---------------------------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION rimuovi_foto_privata() RETURNS TRIGGER AS $$
BEGIN
  DELETE FROM contenuto WHERE id_foto = OLD.id_foto;
  UPDATE frame SET id_foto = NULL WHERE id_foto = OLD.id_foto;
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER rimuovi_foto_privata_dopo_update
AFTER UPDATE OF condivisa ON fotografia
FOR EACH ROW WHEN (NEW.condivisa = false)
EXECUTE PROCEDURE rimuovi_foto_privata();



---------------------------------------------------------------------------------------------------------------------------------------------
-- Trigger che elimina la foto dai video e dalle collezioni quando passa da condivisa a privata
---------------------------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION rimuovi_foto_privata() RETURNS TRIGGER AS $$
BEGIN
  DELETE FROM contenuto WHERE id_foto = OLD.id_foto;
  DELETE FROM frame WHERE id_foto = OLD.id_foto;
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER rimuovi_foto_privata_dopo_update
AFTER UPDATE OF condivisa ON fotografia
FOR EACH ROW WHEN (NEW.condivisa = false)
EXECUTE PROCEDURE rimuovi_foto_privata();



