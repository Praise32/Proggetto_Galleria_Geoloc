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
