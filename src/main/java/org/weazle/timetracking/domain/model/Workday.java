package org.weazle.timetracking.domain.model;

import org.springframework.lang.NonNull;
import org.weazle.timetracking.adapter.api.model.TimeRecord;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Workday {

    private final UUID id;
    private final LocalDate currentDate;
    private final List<TimeRecord> timeRecordList = new ArrayList<>();

    public Workday(@NonNull final UUID id, @NonNull final LocalDate currentDate) {
        this.id = id;
        this.currentDate = currentDate;
    }

    public UUID getId() {
        return id;
    }

    public LocalDate getCurrentDate() {
        return currentDate;
    }

    public List<TimeRecord> getTimeRecordList() {
        return timeRecordList;
    }

    void addTimeRecord(@NonNull final TimeRecord record) {
        timeRecordList.add(record);
    }
}
