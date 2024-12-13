package org.weazle.timetracking.domain.model;

import org.springframework.lang.NonNull;
import org.weazle.timetracking.adapter.api.model.TimeRecord;
import org.weazle.timetracking.adapter.api.model.TimeRecordType;
import org.weazle.timetracking.domain.entity.CalendarEntity;
import org.weazle.timetracking.domain.entity.WorkdayEntity;
import org.weazle.timetracking.domain.model.exceptions.TimeSlotOutOfBoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WorkdayModel {

    private final UUID id;
    private final CalendarEntity calendarReference;
    private WorkdayState currentState;
    private final List<TimeSlotModel> timeSlotModelList = new ArrayList<>();

    public WorkdayModel(@NonNull final UUID id, @NonNull final CalendarEntity calendarReference) {
        this.id = id;
        this.calendarReference = calendarReference;
        this.currentState = WorkdayState.ABSENT; //Default state when creating a new workday
    }

    public WorkdayModel(@NonNull final WorkdayEntity workdayEntity) {
        this.id = workdayEntity.getId();
        this.calendarReference = workdayEntity.getCalendar();

        workdayEntity.getTimeSlots().forEach(timeSlot -> {
            timeSlotModelList.add(new TimeSlotModel(timeSlot));
        });
    }

    public UUID getId() {
        return id;
    }

    public CalendarEntity getCalendarReference() {
        return calendarReference;
    }

    public WorkdayState getCurrentState() {
        return currentState;
    }

    public List<TimeSlotModel> getTimeSlotList() {
        return timeSlotModelList;
    }

    public void recordTime(@NonNull final TimeRecord record) throws TimeSlotOutOfBoundException {
        if (record.getRecordType().equals(TimeRecordType.START_WORK)) {
            timeSlotModelList.add(new TimeSlotModel(record));
        } else {
            timeSlotModelList.getLast().addEndTime(record);
        }

        this.currentState =
                record.getRecordType() == TimeRecordType.START_WORK ? WorkdayState.PRESENT: WorkdayState.ABSENT;
    }
}
