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

---------------------------------------------------------------------------------------------------------------------------------------------
-- Per non avere foto non presenti con nessun autore, possiamo poi mettere in java un avviso che quando un utente elimina la foto dalla collezione
-- lo avvisa che essa sarà eliminata per sempre, anche se magari contiene tag_utente, poichè non possedendo autore, non rispetterebbe il vincolo di autore

-- Quando un utente elimina una foto da una collezione o da un video, controlla se quella fotografia ha l'username_autore "NULL"
-- e se è presente in altre collezioni o video, nel caso non è presente in nessun video o collezione, elimina la fotografia
---------------------------------------------------------------------------------------------------------------------------------------------


CREATE OR REPLACE FUNCTION check_and_delete_photo() RETURNS TRIGGER AS $$
DECLARE
    count_collection INTEGER;
    count_frame INTEGER;
BEGIN
    -- Verifica se la fotografia è presente in una collezione
    SELECT COUNT(*) INTO count_collection
    FROM contenuto
    WHERE id_foto = OLD.id_foto;
    
    -- Verifica se la fotografia è presente in un video
    SELECT COUNT(*) INTO count_frame
    FROM frame
    WHERE id_foto = OLD.id_foto;
    
    -- Se la fotografia non è presente in nessuna collezione o video, la elimina
    IF ((SELECT username_autore FROM fotografia WHERE id_foto = OLD.id_foto) IS NULL AND count_collection = 0 AND count_frame = 0) THEN
        DELETE FROM fotografia WHERE id_foto = OLD.id_foto;
    END IF;

    RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER check_and_delete_photo_trigger_content
AFTER DELETE ON contenuto
FOR EACH ROW
EXECUTE PROCEDURE check_and_delete_photo();

CREATE TRIGGER check_and_delete_photo_trigger_frame
AFTER DELETE ON frame
FOR EACH ROW
EXECUTE PROCEDURE check_and_delete_photo();



---------------------------------------------------------------------------------------------------------------------------------------------
-- Trigger che riordina i frame all'interno di un video dopo l'eliminazione di un frame
---------------------------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION riordina_frame() RETURNS TRIGGER AS $$
BEGIN
    -- Riduci l'ordine dei frame successivi a quello eliminato
    UPDATE frame
    SET ordine = ordine - 1
    WHERE id_video = OLD.id_video AND ordine > OLD.ordine;
    
    RETURN NULL; -- il valore di ritorno non è rilevante per un trigger AFTER DELETE
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER riordina_frame_trigger
AFTER DELETE ON frame
FOR EACH ROW EXECUTE FUNCTION riordina_frame();

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
