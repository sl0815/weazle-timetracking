CREATE TABLE workday (
     id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
     calendar_id INTEGER NOT NULL,
     FOREIGN KEY (calendar_id) REFERENCES calendar(id) ON DELETE CASCADE
);

CREATE TABLE time_slot (
   id SERIAL PRIMARY KEY,
   start_date TIMESTAMPTZ NOT NULL,
   end_date TIMESTAMPTZ DEFAULT NULL,
   workday_id UUID NOT NULL,
   FOREIGN KEY (workday_id) REFERENCES workday(id) ON DELETE CASCADE
);

CREATE INDEX idx_workday_calendar_id ON workday (calendar_id);
CREATE INDEX idx_time_slot_workday_id ON time_slot (workday_id);