package org.weazle.timetracking.domain.model;

import org.springframework.lang.NonNull;
import org.weazle.timetracking.adapter.api.model.TimeRecord;
import org.weazle.timetracking.adapter.api.model.TimeRecordType;
import org.weazle.timetracking.domain.entity.TimeSlotEntity;
import org.weazle.timetracking.domain.model.exceptions.TimeSlotOutOfBoundException;

import java.time.temporal.ChronoUnit;

public class TimeSlotModel {

    private TimeRecord startRecord;
    private TimeRecord endRecord;

    public TimeSlotModel(@NonNull final TimeRecord startRecord) throws TimeSlotOutOfBoundException {
        addStartRecord(startRecord);
    }

    public TimeSlotModel(@NonNull final TimeRecord startRecord, @NonNull final TimeRecord endRecord) throws TimeSlotOutOfBoundException {
        addStartRecord(startRecord);
        addEndRecord(endRecord);
    }

    public TimeSlotModel(@NonNull final TimeSlotEntity timeSlotEntity) {
        this.startRecord = new TimeRecord(timeSlotEntity.getStartDate(), TimeRecordType.START_WORK);

        if (timeSlotEntity.getEndDate() != null) {
            this.endRecord = new TimeRecord(timeSlotEntity.getEndDate(), TimeRecordType.START_WORK);
        }
    }

    public TimeRecord getStartRecord() {
        return startRecord;
    }

    public TimeRecord getEndRecord() {
        return endRecord;
    }

    public long getHoursWorkedInMinutes() {
        return ChronoUnit.MINUTES.between(startRecord.getRecordedTime(), endRecord.getRecordedTime());
    }

    void addEndTime(@NonNull final TimeRecord endRecord) throws TimeSlotOutOfBoundException {
        addEndRecord(endRecord);
    }

    private void addStartRecord(@NonNull final TimeRecord startRecord) throws TimeSlotOutOfBoundException {
        if (startRecord.getRecordType() == TimeRecordType.END_WORK) {
            throw new TimeSlotOutOfBoundException("Cannot create a new time slot with start record type END_WORK.");
        }
        this.startRecord = startRecord;
    }

    private void addEndRecord(@NonNull final TimeRecord endRecord) throws TimeSlotOutOfBoundException {
        if (endRecord.getRecordedTime().isBefore(this.startRecord.getRecordedTime())) {
            throw new TimeSlotOutOfBoundException("End time must be before start time.");
        } else if (endRecord.getRecordType() == TimeRecordType.START_WORK) {
            throw new TimeSlotOutOfBoundException("Cannot add end time with record type START_WORK.");
        }
        this.endRecord = endRecord;
    }
}
