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
