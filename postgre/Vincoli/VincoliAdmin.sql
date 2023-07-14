---------------------------------------------------------------------------------------------------------------------------------------------
--“L’amministratore del sistema può eliminare un utente: in tal caso, tutte le foto dell’utente verranno cancellate dalla libreria, eccetto
--quelle che contengono come soggetto un altro degli utenti della galleria condivisa."

--Per eseguire questo vincolo setteremo a NULL tutte le foto che ha scattato l'utente dove ha un tag_utente in questo modo quando andremo ad eliminare
--L'utente verranno eliminate solo le foto non condivise grazie al "Delete on Cascade" nella tabella fotografia
---------------------------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION save_tagphoto()
RETURNS TRIGGER AS
$$
BEGIN
	UPDATE fotografia
	SET username_autore = NULL
	WHERE username_autore= OLD.username AND condivisa = TRUE AND EXISTS (
		SELECT * FROM tag_utente
		WHERE tag_utente.id_foto = fotografia.id_foto AND tag_utente.username<>fotografia.username_autore	
	);
	RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER save_tagphoto_trigger
BEFORE DELETE ON utente
FOR EACH ROW
EXECUTE FUNCTION save_tagphoto();

--current_user è un'identità di sessione predefinita in PostgreSQL
CREATE TRIGGER delete_user_and_set_autore_fotografia_null_trigger
BEFORE DELETE ON utente
FOR EACH ROW
EXECUTE FUNCTION delete_user_and_set_autore_fotografia_null(current_user);

---------------------------------------------------------------------------------------------------------------------------------------------
-- Oltre a settare a NULL l'username_autore dobbiamo anche verificare che la foto condivisa sia effettivamente presente in qualche video o collezione
-- Se autore_username è NULL e la fotografia non è presente in nessuna collezione o video, la fotografia viene eliminata
---------------------------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION check_and_delete_photo() RETURNS TRIGGER AS $$
DECLARE
    count_collection INTEGER;
    count_video INTEGER;
BEGIN
    -- Verifica se la fotografia è presente in una collezione
    SELECT COUNT(*) INTO count_collection
    FROM contenuto
    WHERE id_foto = NEW.id_foto;
    
    -- Verifica se la fotografia è presente in un video
    SELECT COUNT(*) INTO count_video
    FROM frame
    WHERE id_foto = NEW.id_foto;
    
    -- Se la fotografia non è presente in nessuna collezione o video, la elimina
    IF (NEW.username_autore IS NULL AND count_collection = 0 AND count_video = 0) THEN
        DELETE FROM fotografia WHERE id_foto = NEW.id_foto;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER check_and_delete_photo_trigger
AFTER UPDATE ON fotografia
FOR EACH ROW
EXECUTE PROCEDURE check_and_delete_photo();



---------------------------------------------------------------------------------------------------------------------------------------------
-- Trigger che vieta ad un amministatore di eliminare altri amministratori di sistema
---------------------------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION verify_admin()
RETURNS TRIGGER AS $$
BEGIN
    -- Verifica che l'utente da eliminare non sia un admin
    IF OLD.admin = TRUE AND (SELECT COUNT(*) FROM utente WHERE admin = true) = 1 THEN
        RAISE EXCEPTION 'Non è possibile eliminare l unico utente amministratore';
    END IF;

    RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER verify_admin_trigger
BEFORE DELETE ON utente
FOR EACH ROW
EXECUTE FUNCTION verify_admin();
