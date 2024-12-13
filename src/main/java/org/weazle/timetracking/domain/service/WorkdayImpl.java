package org.weazle.timetracking.domain.service;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.weazle.timetracking.domain.entity.CalendarEntity;
import org.weazle.timetracking.domain.entity.TimeSlotEntity;
import org.weazle.timetracking.domain.entity.WorkdayEntity;
import org.weazle.timetracking.domain.model.WorkdayModel;
import org.weazle.timetracking.domain.repository.CalendarRepository;
import org.weazle.timetracking.domain.repository.WorkdayRepository;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class WorkdayImpl implements WorkdayService {

    @NonNull
    private final WorkdayRepository workdayRepository;

    @NonNull
    private final CalendarRepository calendarRepository;

    public WorkdayImpl(
            @NonNull final WorkdayRepository workdayRepository,
            @NonNull final CalendarRepository calendarRepository
    ) {
        this.workdayRepository = workdayRepository;
        this.calendarRepository = calendarRepository;
    }

    @Override
    public WorkdayModel getWorkdayByUUID(@NonNull final UUID workdayId) {
        return new WorkdayModel(
                workdayRepository.getReferenceById(workdayId)
        );
    }

    @Override
    public WorkdayModel createWorkday() {
        CalendarEntity calendarEntity = calendarRepository.findByActualDate(LocalDate.now());

        return new WorkdayModel(UUID.randomUUID(), calendarEntity);
    }

    @Override
    public void updateWorkday(WorkdayModel workdayModel) {
        WorkdayEntity workdayEntity = new WorkdayEntity();

        workdayEntity.setId(workdayModel.getId());
        workdayEntity.setCalendar(workdayModel.getCalendarReference());
        workdayEntity.setTimeSlots(
                workdayModel.getTimeSlotList().stream()
                        .map(timeSlotModel -> {
                            TimeSlotEntity timeSlotEntity = new TimeSlotEntity();

                            timeSlotEntity.setWorkday(workdayEntity);
                            timeSlotEntity.setStartDate(timeSlotModel.getStartRecord().getRecordedTime());

                            if (timeSlotModel.getEndRecord() != null) {
                                timeSlotEntity.setEndDate(timeSlotModel.getEndRecord().getRecordedTime());
                            }

                            return timeSlotEntity;
                        })
                        .toList()
        );

        workdayRepository.save(workdayEntity);
    }
}
