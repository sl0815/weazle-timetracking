package org.weazle.timetracking.domain.model;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.weazle.timetracking.adapter.api.model.TimeRecord;
import org.weazle.timetracking.adapter.api.model.TimeRecordType;
import org.weazle.timetracking.domain.model.exceptions.TimeSlotOutOfBoundException;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class WorkdayTest {

    @Test
    void testItShouldAddAValidTimeRecordToAWorkday() throws TimeSlotOutOfBoundException {
        ZonedDateTime now = ZonedDateTime.now();
        TimeRecord record = new TimeRecord(now, TimeRecordType.START_WORK);

        Workday currentWorkday = new Workday(UUID.randomUUID(), LocalDate.now());
        assertThat(currentWorkday.getCurrentState()).isEqualTo(WorkdayState.ABSENT);
        currentWorkday.recordTime(record);

        assertThat(currentWorkday.getTimeSlotList()).size().isEqualTo(1);
        assertThat(currentWorkday.getTimeSlotList().getFirst().getStartRecord().getRecordedTime()).isEqualTo(now);
        assertThat(currentWorkday.getTimeSlotList().getFirst().getStartRecord().getRecordType()).isEqualTo(TimeRecordType.START_WORK);
    }
}
