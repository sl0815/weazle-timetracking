package org.weazle.timetracking.domain.model;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.weazle.timetracking.adapter.api.model.TimeRecord;
import org.weazle.timetracking.adapter.api.model.TimeRecordType;
import org.weazle.timetracking.domain.entity.CalendarEntity;
import org.weazle.timetracking.domain.model.exceptions.TimeSlotOutOfBoundException;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class WorkdayModelTest {

    @Test
    void testItShouldAddAValidTimeRecordToAWorkday() throws TimeSlotOutOfBoundException {
        ZonedDateTime now = ZonedDateTime.now();
        TimeRecord record = new TimeRecord(now, TimeRecordType.START_WORK);

        CalendarEntity calendar = new CalendarEntity(LocalDate.now());

        WorkdayModel currentWorkdayModel = new WorkdayModel(UUID.randomUUID(), calendar);
        assertThat(currentWorkdayModel.getCurrentState()).isEqualTo(WorkdayState.ABSENT);
        currentWorkdayModel.recordTime(record);

        assertThat(currentWorkdayModel.getTimeSlotList()).size().isEqualTo(1);
        assertThat(currentWorkdayModel.getTimeSlotList().getFirst().getStartRecord().getRecordedTime()).isEqualTo(now);
        assertThat(currentWorkdayModel.getTimeSlotList().getFirst().getStartRecord().getRecordType()).isEqualTo(TimeRecordType.START_WORK);
    }
}
