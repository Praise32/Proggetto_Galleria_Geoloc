---------------------------------------------------------------------------------------------------------------------------------------------
--“L’amministratore del sistema può eliminare un utente: in tal caso, tutte le foto dell’utente verranno cancellate dalla libreria, eccetto
--quelle che contengono come soggetto un altro degli utenti della galleria condivisa."

--Per eseguire questo vincolo setteremo a NULL tutte le foto che ha scattato l'utente che sono condivise, in questo modo quando andremo ad eliminare
--L'utente verranno eliminate solo le foto non condivise grazie al "Delete on Cascade" nella tabella fotografia
---------------------------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION delete_user_and_set_autore_fotografia_null(utente_corrente utente.username%TYPE)
RETURNS TRIGGER AS $$
DECLARE
  is_current_user_admin BOOLEAN;
BEGIN
  -- Verifica se l'utente corrente ha il ruolo di amministratore
  SELECT admin INTO is_current_user_admin FROM utente WHERE username = utente_corrente;

  IF is_current_user_admin THEN
    -- Imposta a NULL il campo "username_autore" nelle fotografie condivise dell'utente eliminato
    IF OLD.admin THEN
      UPDATE fotografia
      SET username_autore = NULL
      WHERE username_autore = OLD.username AND condivisa = true;
    END IF;

    RETURN OLD;
  ELSE
    RAISE EXCEPTION 'Solo gli admin possono eliminare gli altri utenti';
  END IF;
END;
$$ LANGUAGE plpgsql;


--current_user è un'identità di sessione predefinita in PostgreSQL
CREATE TRIGGER delete_user_and_set_autore_fotografia_null_trigger
BEFORE DELETE ON utente
FOR EACH ROW
EXECUTE FUNCTION delete_user_and_set_autore_fotografia_null(current_user);


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
