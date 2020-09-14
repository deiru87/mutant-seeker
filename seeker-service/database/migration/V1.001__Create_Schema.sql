DROP TABLE IF EXISTS log_event_mobile;
DROP TABLE IF EXISTS log_event_mobile_rules;

CREATE TABLE dna (
id BIGSERIAL PRIMARY KEY,
dna_code varchar NOT NULL,
mutant BOOLEAN,
human BOOLEAN,
CONSTRAINT dna_id_key UNIQUE (dna_code)
);

CREATE TABLE consolidate_dna (
id BIGSERIAL PRIMARY KEY,
amount_mutant INTEGER NOT NULL DEFAULT 0,
amount_human INTEGER NOT NULL DEFAULT 0
);

CREATE OR REPLACE FUNCTION consolidate_data()
  RETURNS TRIGGER
  LANGUAGE PLPGSQL
  AS
$function$
DECLARE
    amount_mut INTEGER default 0;
    amount_hum INTEGER default 0;
BEGIN

    IF NEW.mutant THEN
      amount_mut = 1;
    ELSE
      amount_hum = 1;
    END IF;

		 INSERT INTO consolidate_dna(id,amount_mutant,amount_human)
		 VALUES(1,amount_mut,amount_hum) ON CONFLICT(id) DO update set amount_mutant = consolidate_dna.amount_mutant + amount_mut,
		 amount_human = consolidate_dna.amount_human + amount_hum;

	RETURN NEW;
END;
$function$
;

CREATE TRIGGER exec_consolidate_data
  AFTER INSERT
  ON dna
  FOR EACH ROW
  EXECUTE PROCEDURE consolidate_data();

