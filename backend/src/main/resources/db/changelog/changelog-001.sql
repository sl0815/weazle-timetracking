CREATE TABLE calendar (
      id SERIAL PRIMARY KEY,
      date_actual DATE UNIQUE,
      year INTEGER,
      quarter INTEGER,
      month INTEGER,
      month_name TEXT,
      day_of_month INTEGER,
      day_of_week TEXT,
      week_of_year INTEGER,
      is_weekday BOOLEAN
);

CREATE INDEX idx_year_month ON calendar (year, month);
CREATE INDEX idx_week_of_year ON calendar (week_of_year);

DO $$
DECLARE
v_current_date DATE := '2024-01-01';
    v_end_date DATE := '2045-12-31';
BEGIN
    WHILE v_current_date <= v_end_date LOOP
        INSERT INTO calendar (
            date_actual,
            year,
            quarter,
            month,
            month_name,
            day_of_month,
            day_of_week,
            week_of_year,
            is_weekday
        )
        VALUES (
            v_current_date,
            EXTRACT(YEAR FROM v_current_date),
            EXTRACT(QUARTER FROM v_current_date),
            EXTRACT(MONTH FROM v_current_date),
            UPPER(TO_CHAR(v_current_date, 'FMMonth')),
            EXTRACT(DAY FROM v_current_date),
            UPPER(TO_CHAR(v_current_date, 'FMDay')),
            EXTRACT(WEEK FROM v_current_date),
            EXTRACT(DOW FROM v_current_date) BETWEEN 1 AND 5
        );

        v_current_date := v_current_date + 1;
END LOOP;
END$$;
