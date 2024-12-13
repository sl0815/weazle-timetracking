package org.weazle.timetracking.domain.service;

import org.springframework.lang.NonNull;
import org.weazle.timetracking.domain.model.WorkdayModel;

import java.util.UUID;

public interface WorkdayService {
    WorkdayModel getWorkdayByUUID(@NonNull final UUID workdayId);
    WorkdayModel createWorkday();
    void updateWorkday(@NonNull final WorkdayModel workday);
}
