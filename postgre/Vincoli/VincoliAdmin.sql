---------------------------------------------------------------------------------------------------------------------------------------------
--“L’amministratore del sistema può eliminare un utente: in tal caso, tutte le foto dell’utente verranno cancellate dalla libreria, eccetto
--quelle che contengono come soggetto un altro degli utenti della galleria condivisa."
---------------------------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION delete_user(utente_corrente utente.username%TYPE)
RETURNS TRIGGER AS 
$$BEGIN   
 -- Verifica se l'utente corrente ha il ruolo di amministratore   
 IF EXISTS (SELECT 1 FROM utente WHERE username = utente_corrente AND admin = true) THEN   
     -- Esegue l'eliminazione dell'utente 
       DELETE FROM utente WHERE username = OLD.username;
        RETURN OLD;   
 ELSE       
 RAISE EXCEPTION 'Solo gli admin possono eliminare gli altri utenti';    
END IF;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER delete_user_trigger
BEFORE DELETE ON utente
FOR EACH ROW
EXECUTE FUNCTION delete_userv();



---------------------------------------------------------------------------------------------------------------------------------------------
--Da cambiare con quella che ha messo genny
---------------------------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION prevent_photo_deletion_with_shared_subject()
RETURNS TRIGGER AS
$$
DECLARE
    shared_subject_photo_exists BOOLEAN;
BEGIN
    -- Controlla se esiste una foto con un altro utente della galleria condivisa come soggetto
    SELECT EXISTS (
        SELECT 1
        FROM fotografia f
        JOIN tag_utente tu ON f.id_foto = tu.id_foto
        WHERE f.username_autore = OLD.username AND tu.username != OLD.username
    ) INTO shared_subject_photo_exists;

    -- Se esiste una foto con un altro utente come soggetto, interrompi l'eliminazione e solleva un'eccezione
    IF shared_subject_photo_exists THEN
        RAISE EXCEPTION 'Non è possibile eliminare l''utente poiché esiste una foto condivisa con un altro utente come soggetto';
    END IF;

    RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER prevent_photo_deletion_with_shared_subject_trigger
BEFORE DELETE ON utente
FOR EACH ROW
EXECUTE FUNCTION prevent_photo_deletion_with_shared_subject();


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
EXECUTE FUNCTION verifica_admin();
