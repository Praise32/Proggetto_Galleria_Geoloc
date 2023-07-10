--Trigger che aggiorna gli elementi di una galleria
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


-- Trigger che impedisce ad un'utente di inserire all'interno di una Galleria delle
-- Fotografie  private se non è l'autore di esse 
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

    --Trigger che impedisce ad un'utente che di utilizzare foto private se non ne è l'autore nei video

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





-- Trigger per aggiornare automaticamente il valore dell'attributo "numero_frames"
-- quando si inserisce o si elimina un frame nella tabella "frame":
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

-- Trigger per generare automaticamente il valore dell'attributo "ordine"
-- quando si inserisce un nuovo frame nella tabella "frame":
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



CREATE OR REPLACE FUNCTION verify_admin()
RETURNS TRIGGER AS $$
BEGIN
    -- Verifica che l'utente da eliminare non sia un admin
    IF OLD.admin = TRUE AND (SELECT COUNT(*) FROM utente WHERE admin = true) = 1 THEN
        RAISE EXCEPTION 'Non è possibile eliminare l''unico utente amministratore';
    END IF;

    RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER verify_admin_trigger
BEFORE DELETE ON utente
FOR EACH ROW
EXECUTE FUNCTION verify_admin();





--TRIGGER D'INTEGRITA REFERENZIALE
/*L'uso di "SELECT 1" è una convenzione comune per eseguire una verifica di 
esistenza di una riga senza dover recuperare tutti i dati della riga stessa. 
E' possibile scegliere "SELECT *" che restituirà tutti i dati della riga, 
anche se potrebbe essere utile in alcuni casi, ne inficeranno le prestazioni del database
*/



-- Trigger per la tabella "fotografia"
CREATE OR REPLACE FUNCTION check_fotografia_luogo_fk() RETURNS TRIGGER AS $check_fotografia_luogo_fk$
BEGIN
  IF NOT EXISTS (SELECT 1 FROM luogo WHERE latitudine = NEW.latitudine AND longitudine = NEW.longitudine) THEN
    RAISE EXCEPTION 'Il luogo non esiste';
  END IF;
  RETURN NEW;
END;
$check_fotografia_luogo_fk$ LANGUAGE plpgsql;

CREATE TRIGGER fotografia_luogo_fk_trigger
  BEFORE INSERT OR UPDATE ON fotografia
  FOR EACH ROW
  EXECUTE FUNCTION check_fotografia_luogo_fk();



-- Trigger per la tabella "contenuto"
CREATE OR REPLACE FUNCTION check_contenuto_collezione_fk() RETURNS TRIGGER AS $check_contenuto_collezione_fk$
BEGIN
  IF NOT EXISTS (SELECT 1 FROM collezione WHERE id_collezione = NEW.id_collezione) THEN
    RAISE EXCEPTION 'La collezione non esiste';
  END IF;
  RETURN NEW;
END;
$check_contenuto_collezione_fk$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION check_contenuto_fotografia_fk() RETURNS TRIGGER AS $check_contenuto_fotografia_fk$
BEGIN
  IF NOT EXISTS (SELECT 1 FROM fotografia WHERE id_foto = NEW.id_foto) THEN
    RAISE EXCEPTION 'La fotografia non esiste';
  END IF;
  RETURN NEW;
END;
$check_contenuto_fotografia_fk$ LANGUAGE plpgsql;

CREATE TRIGGER contenuto_collezione_fk_trigger
  BEFORE INSERT OR UPDATE ON contenuto
  FOR EACH ROW
  EXECUTE FUNCTION check_contenuto_collezione_fk();

CREATE TRIGGER contenuto_fotografia_fk_trigger
  BEFORE INSERT OR UPDATE ON contenuto
  FOR EACH ROW
  EXECUTE FUNCTION check_contenuto_fotografia_fk();

CREATE TRIGGER save_tagphoto_trigger
BEFORE DELETE ON utente
FOR EACH ROW
EXECUTE FUNCTION save_tagphoto();

CREATE OR REPLACE FUNCTION save_tagphoto()
RETURN TRIGGER AS
$$
BEGIN
	UPDATE fotografia
	SET username = NULL
	WHERE username= OLD.username AND condivisa = TRUE AND EXISTS (
		SELECT * FROM tag_utente
		WHERE tag_utente.id_foto = OLD.id_foto AND tag_utente.username<>fotografia.username		
	);
	RETURN OLD;
END;
$$ LANGUAGE plpgsql;


-- Trigger per la tabella "tag_utente"
CREATE OR REPLACE FUNCTION check_tag_utente_utente_fk() RETURNS TRIGGER AS $check_tag_utente_utente_fk$
BEGIN
  IF NOT EXISTS (SELECT 1 FROM utente WHERE username = NEW.username) THEN
    RAISE EXCEPTION 'L''utente non esiste';
  END IF;
  RETURN NEW;
END;
$check_tag_utente_utente_fk$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION check_tag_utente_fotografia_fk() RETURNS TRIGGER AS $check_tag_utente_fotografia_fk$
BEGIN
  IF NOT EXISTS (SELECT 1 FROM fotografia WHERE id_foto = NEW.id_foto) THEN
    RAISE EXCEPTION 'La fotografia non esiste';
  END IF;
  RETURN NEW;
END;
$check_tag_utente_fotografia_fk$ LANGUAGE plpgsql;

CREATE TRIGGER tag_utente_utente_fk_trigger
  BEFORE INSERT OR UPDATE ON tag_utente
  FOR EACH ROW
  EXECUTE FUNCTION check_tag_utente_utente_fk();

CREATE TRIGGER tag_utente_fotografia_fk_trigger
  BEFORE INSERT OR UPDATE ON tag_utente
  FOR EACH ROW
  EXECUTE FUNCTION check_tag_utente_fotografia_fk();





-- Trigger per la tabella "tag_soggetto"
CREATE OR REPLACE FUNCTION check_tag_soggetto_soggetto_fk() RETURNS TRIGGER AS $check_tag_soggetto_soggetto_fk$
BEGIN
  IF NOT EXISTS (SELECT 1 FROM soggetto WHERE nome = NEW.nome_soggetto) THEN
    RAISE EXCEPTION 'Il soggetto non esiste';
  END IF;
  RETURN NEW;
END;
$check_tag_soggetto_soggetto_fk$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION check_tag_soggetto_fotografia_fk() RETURNS TRIGGER AS $check_tag_soggetto_fotografia_fk$
BEGIN
  IF NOT EXISTS (SELECT 1 FROM fotografia WHERE id_foto = NEW.id_foto) THEN
    RAISE EXCEPTION 'La fotografia non esiste';
  END IF;
  RETURN NEW;
END;
$check_tag_soggetto_fotografia_fk$ LANGUAGE plpgsql;

CREATE TRIGGER tag_soggetto_soggetto_fk_trigger
  BEFORE INSERT OR UPDATE ON tag_soggetto
  FOR EACH ROW
  EXECUTE FUNCTION check_tag_soggetto_soggetto_fk();

CREATE TRIGGER tag_soggetto_fotografia_fk_trigger
  BEFORE INSERT OR UPDATE ON tag_soggetto
  FOR EACH ROW
  EXECUTE FUNCTION check_tag_soggetto_fotografia_fk();
