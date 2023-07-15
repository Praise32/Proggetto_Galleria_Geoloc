--MI SENTO RITARDATO

/*
CHAT GTP4 DICE QUESTO COMUNQUE QUINDI TUTTI QUESTI VINCOLI SONO INUTILI

Nel tuo schema, i vincoli di integrità referenziale sono già definiti attraverso i vincoli FOREIGN KEY in ogni tabella. 
Questi vincoli assicurano che i dati nelle tabelle collegate rimangano consistenti. 
Per esempio, i vincoli ON DELETE CASCADE assicurano che quando un record padre viene eliminato, anche tutti i record figli corrispondenti vengono eliminati. 
Allo stesso modo, il vincolo ON DELETE SET NULL imposta il valore della chiave esterna a NULL quando il record padre corrispondente viene eliminato.

I trigger che hai creato non sono inutili, ma sono in gran parte ridondanti se hai già definito i vincoli di chiave esterna (FOREIGN KEY) nelle tue tabelle. 
I vincoli di chiave esterna in SQL fanno esattamente quello che fanno questi trigger: 
verificano che un valore inserito in una colonna esista come un valore chiave in un'altra tabella. 
Se il valore non esiste, l'operazione di inserimento o di aggiornamento fallisce.

*/












---------------------------------------------------------------------------------------------------------------------------------------------
--TRIGGER D'INTEGRITA REFERENZIALE
/*
L'uso di "SELECT 1" è una convenzione comune per eseguire una verifica di 
esistenza di una riga senza dover recuperare tutti i dati della riga stessa. 
E' possibile scegliere "SELECT *" che restituirà tutti i dati della riga, 
anche se potrebbe essere utile in alcuni casi, ne inficeranno le prestazioni del database
*/
---------------------------------------------------------------------------------------------------------------------------------------------



---------------------------------------------------------------------------------------------------------------------------------------------
-- Trigger per la tabella "fotografia"
---------------------------------------------------------------------------------------------------------------------------------------------

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



---------------------------------------------------------------------------------------------------------------------------------------------
-- Trigger per la tabella "contenuto"
---------------------------------------------------------------------------------------------------------------------------------------------

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



---------------------------------------------------------------------------------------------------------------------------------------------
-- Trigger per la tabella "tag_utente"
---------------------------------------------------------------------------------------------------------------------------------------------
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



---------------------------------------------------------------------------------------------------------------------------------------------
-- Trigger per la tabella "tag_soggetto"
---------------------------------------------------------------------------------------------------------------------------------------------

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
