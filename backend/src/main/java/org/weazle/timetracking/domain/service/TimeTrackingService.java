package org.weazle.timetracking.domain.service;

import org.springframework.lang.NonNull;
import org.weazle.timetracking.adapter.api.model.TimeRecord;
import org.weazle.timetracking.domain.model.WorkdayModel;

import java.util.UUID;

public interface TimeTrackingService {
    WorkdayModel recordTime(@NonNull final TimeRecord timeRecord);
    WorkdayModel recordTimeForWorkday(@NonNull final UUID workdayUUID, @NonNull final TimeRecord timeRecord);
}
