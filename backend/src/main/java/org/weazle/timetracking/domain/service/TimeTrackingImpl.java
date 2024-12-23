package org.weazle.timetracking.domain.service;

import org.springframework.lang.NonNull;
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
    public WorkdayModel recordTime(@NonNull final TimeRecord record) {
        WorkdayModel workdayModel = workdayService.createNewWorkday();

        return updateWorkday(record, workdayModel);
    }

    @Override
    public WorkdayModel recordTimeForWorkday(@NonNull final UUID workdayUUID, @NonNull final TimeRecord timeRecord) {
        WorkdayModel workdayModel = workdayService.getWorkdayByUUID(workdayUUID);

        return updateWorkday(timeRecord, workdayModel);
    }

    private WorkdayModel updateWorkday(@NonNull final TimeRecord record, @NonNull final WorkdayModel workdayModel) {
        try {
            workdayModel.recordTime(record);
        } catch (TimeSlotOutOfBoundException e) {
            throw new RuntimeException(e);
        }

        workdayService.updateWorkday(workdayModel);

        return workdayModel;
    }
}