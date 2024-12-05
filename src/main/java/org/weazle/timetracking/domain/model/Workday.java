package org.weazle.timetracking.domain.model;

import org.springframework.lang.NonNull;
import org.weazle.timetracking.adapter.api.model.TimeRecord;
import org.weazle.timetracking.adapter.api.model.TimeRecordType;
import org.weazle.timetracking.domain.model.exceptions.TimeSlotOutOfBoundException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Workday {

    private final UUID id;
    private final LocalDate currentDate;
    private WorkdayState currentState;
    private final List<TimeSlot> timeSlotList = new ArrayList<>();

    public Workday(@NonNull final UUID id, @NonNull final LocalDate currentDate) {
        this.id = id;
        this.currentDate = currentDate;
        this.currentState = WorkdayState.ABSENT; //Default state when creating a new workday
    }

    public UUID getId() {
        return id;
    }

    public LocalDate getCurrentDate() {
        return currentDate;
    }

    public WorkdayState getCurrentState() {
        return currentState;
    }

    public List<TimeSlot> getTimeSlotList() {
        return timeSlotList;
    }

    public void recordTime(@NonNull final TimeRecord record) throws TimeSlotOutOfBoundException {
        if (record.getRecordType().equals(TimeRecordType.START_WORK)) {
            timeSlotList.add(new TimeSlot(record));
        } else {
            timeSlotList.getLast().addEndTime(record);
        }

        this.currentState =
                record.getRecordType() == TimeRecordType.START_WORK ? WorkdayState.PRESENT: WorkdayState.ABSENT;
    }
}
