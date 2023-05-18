CREATE OR REPLACE FUNCTION aggiorna_numero_elementi()
RETURNS TRIGGER AS $$
BEGIN
    UPDATE collezione
    SET numero_elementi = (SELECT COUNT(*) FROM collezione WHERE id_collezione = NEW.id_collezione)
    WHERE id_collezione = NEW.id_collezione;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER aggiorna_numero_elementi_trigger
AFTER INSERT ON collezione
FOR EACH ROW
EXECUTE FUNCTION aggiorna_numero_elementi();

CREATE OR REPLACE FUNCTION verify_admin()
RETURNS TRIGGER AS $$
BEGIN
    -- Verifica che l'utente da eliminare non sia un admin
    IF OLD.admin = TRUE AND (SELECT COUNT(*) FROM utente WHERE admin = true) = 1 THEN
        RAISE EXCEPTION 'Non è possibile eliminare l\'unico utente amministratore';
    END IF;

    RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER verify_admin_trigger
BEFORE DELETE ON utente
FOR EACH ROW
EXECUTE FUNCTION verifica_admin();
