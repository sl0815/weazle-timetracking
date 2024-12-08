CREATE TABLE calendar (
                          date_actual DATE PRIMARY KEY,         -- Das Datum (Primärschlüssel)
                          year INTEGER,                         -- Jahr
                          quarter INTEGER,                      -- Quartal (1-4)
                          month INTEGER,                        -- Monat (1-12)
                          month_name TEXT,                      -- Name des Monats (z.B. 'Januar')
                          day_of_month INTEGER,                 -- Tag des Monats (1-31)
                          day_of_week TEXT,                     -- Name des Wochentages (z.B. 'Montag')
                          week_of_year INTEGER,                 -- Woche des Jahres (1-52)
                          is_weekday BOOLEAN                    -- Flag, ob der Tag ein Wochentag ist (Montag-Freitag)
);

CREATE INDEX idx_year_month ON calendar (year, month); --Index auf Jahr und Monat (nützlich für Jahres-/Monatsabfragen)
CREATE INDEX idx_week_of_year ON calendar (week_of_year); --Index auf Woche des Jahres (nützlich für Woche-Abfragen):

DO $$
DECLARE
v_current_date DATE := '2024-01-01';  -- Startdatum
    v_end_date DATE := '2045-12-31';  -- Enddatum
BEGIN
    WHILE v_current_date <= v_end_date LOOP
        INSERT INTO calendar (
            date_actual,
            year,
            quarter,
            month,
            month_name,  -- Monat als Name
            day_of_month,
            day_of_week,  -- Wochentag als Name
            week_of_year,
            is_weekday  -- Montag bis Freitag als Boolean
        )
        VALUES (
            v_current_date,
            EXTRACT(YEAR FROM v_current_date),  -- Jahr
            EXTRACT(QUARTER FROM v_current_date),  -- Quartal
            EXTRACT(MONTH FROM v_current_date),  -- Monat
            UPPER(TO_CHAR(v_current_date, 'FMMonth')),  -- Name des Monats
            EXTRACT(DAY FROM v_current_date),  -- Tag des Monats
            UPPER(TO_CHAR(v_current_date, 'FMDay')),  -- Name des Wochentages
            EXTRACT(WEEK FROM v_current_date),  -- Woche des Jahres
            EXTRACT(DOW FROM v_current_date) BETWEEN 1 AND 5  -- Wochentag (Montag bis Freitag)
        );

        -- Erhöhe das Datum um 1 Tag
        v_current_date := v_current_date + 1;
END LOOP;
END$$;
