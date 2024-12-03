package org.weazle.timetracking.domain.model;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.weazle.timetracking.adapter.api.model.TimeRecord;
import org.weazle.timetracking.adapter.api.model.TimeRecordType;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class WorkdayTest {

    @Test
    void testItShouldAddAValidTimeRecordToAWorkday() {
        ZonedDateTime now = ZonedDateTime.now();
        TimeRecord record = new TimeRecord(now, TimeRecordType.START_WORK);

        Workday currentWorkday = new Workday(UUID.randomUUID(), LocalDate.now());
        assertThat(currentWorkday.getCurrentState()).isEqualTo(WorkdayState.ABSENT);
        currentWorkday.addTimeRecord(record);

        assertThat(currentWorkday.getTimeRecordList()).size().isEqualTo(1);
        assertThat(currentWorkday.getTimeRecordList().getFirst().getRecordedTime()).isEqualTo(now);
        assertThat(currentWorkday.getTimeRecordList().getFirst().getRecordType()).isEqualTo(TimeRecordType.START_WORK);
    }
}
