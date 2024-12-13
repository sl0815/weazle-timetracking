package org.weazle.timetracking.domain.service;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.weazle.timetracking.adapter.api.model.TimeRecord;
import org.weazle.timetracking.domain.model.WorkdayModel;

import java.util.UUID;

public interface TimeTrackingService {
    WorkdayModel recordTime(@Nullable UUID workdayUUID, @NonNull final TimeRecord record);
}
