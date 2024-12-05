package org.weazle.timetracking.domain.service;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.weazle.timetracking.adapter.api.model.TimeRecord;
import org.weazle.timetracking.domain.model.Workday;
import org.weazle.timetracking.domain.model.exceptions.TimeSlotOutOfBoundException;

import java.time.ZonedDateTime;
import java.util.UUID;

@Service
public class TimeTrackingImpl implements TimeTracking {

    @Override
    public Workday recordTime(@Nullable UUID workdayUUID, @NonNull final TimeRecord record) {
        Workday workday = null;

        if (workdayUUID == null) {
            workday = new Workday(UUID.randomUUID(), ZonedDateTime.now().toLocalDate());
        } else {
            // @ToDo Implement Persistence
        }

        try {
            workday.recordTime(record);
        } catch (TimeSlotOutOfBoundException e) {
            throw new RuntimeException(e);
        }

        return workday;
    }
}
