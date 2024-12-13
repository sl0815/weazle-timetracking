package org.weazle.timetracking.domain.service;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.weazle.timetracking.adapter.api.model.TimeRecord;
import org.weazle.timetracking.domain.model.WorkdayModel;
import org.weazle.timetracking.domain.model.exceptions.TimeSlotOutOfBoundException;

import java.util.UUID;

@Service
public class TimeTrackingImpl implements TimeTrackingService {

    @NonNull
    private final WorkdayService workdayService;

    public TimeTrackingImpl(@NonNull final WorkdayService workdayService) {
        this.workdayService = workdayService;
    }

    @Override
    public WorkdayModel recordTime(@Nullable UUID workdayUUID, @NonNull final TimeRecord record) {
        WorkdayModel workdayModel = null;

        if (workdayUUID == null) {
            workdayModel = workdayService.createWorkday();
        }

        try {
            workdayModel.recordTime(record);
        } catch (TimeSlotOutOfBoundException e) {
            throw new RuntimeException(e);
        }

        workdayService.updateWorkday(workdayModel);

        return workdayModel;
    }
}
