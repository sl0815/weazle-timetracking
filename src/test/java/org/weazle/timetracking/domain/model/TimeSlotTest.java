package org.weazle.timetracking.domain.model;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.weazle.timetracking.adapter.api.model.TimeRecord;
import org.weazle.timetracking.adapter.api.model.TimeRecordType;
import org.weazle.timetracking.domain.model.exceptions.TimeSlotOutOfBoundException;

import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class TimeSlotTest {

    @Test
    void testItShouldCreateATimeSlotContainingOnlyAValidStartTime() throws TimeSlotOutOfBoundException {
        ZonedDateTime rightNow = ZonedDateTime.now();
        TimeRecord startRecord = new TimeRecord(rightNow, TimeRecordType.START_WORK);
        TimeSlot timeSlot = new TimeSlot(startRecord);

        assertThat(timeSlot.getStartRecord()).isNotNull();
        assertThat(timeSlot.getEndRecord()).isNull();
        assertThat(timeSlot.getStartRecord().getRecordedTime()).isEqualTo(rightNow);
    }

    @Test
    void testItShouldCreateATimeSlotContainingAValidStartAndEndTime() throws TimeSlotOutOfBoundException {
        ZonedDateTime rightNow = ZonedDateTime.now();
        ZonedDateTime rightNowInThreeHours = rightNow.plusHours(3);

        TimeRecord startRecord = new TimeRecord(rightNow, TimeRecordType.START_WORK);
        TimeRecord endRecord = new TimeRecord(rightNowInThreeHours, TimeRecordType.END_WORK);

        TimeSlot timeSlot = new TimeSlot(startRecord, endRecord);

        assertThat(timeSlot.getStartRecord()).isNotNull();
        assertThat(timeSlot.getEndRecord()).isNotNull();

        assertThat(timeSlot.getStartRecord().getRecordedTime()).isEqualTo(rightNow);
        assertThat(timeSlot.getEndRecord().getRecordedTime()).isEqualTo(rightNowInThreeHours);
    }

    @Test
    void testItShouldThrowAnExceptionWhenTryingToCreateWithAndEndTimeWhichIsBeforeTheStartTime() {
        ZonedDateTime rightNow = ZonedDateTime.now();
        ZonedDateTime rightNowMinusThreeHours = rightNow.minusHours(3);

        TimeRecord startRecord = new TimeRecord(rightNow, TimeRecordType.START_WORK);
        TimeRecord endRecord = new TimeRecord(rightNowMinusThreeHours, TimeRecordType.END_WORK);

        assertThatThrownBy(() -> new TimeSlot(startRecord, endRecord))
                .isInstanceOf(TimeSlotOutOfBoundException.class)
                .hasMessage("End time must be before start time.");
    }

    @Test
    void testItShouldThrowAnExceptionWhenTryingToAddAndEndTimeWhichIsBeforeTheStartTime() {
        ZonedDateTime rightNow = ZonedDateTime.now();
        ZonedDateTime rightNowMinusThreeHours = rightNow.minusHours(3);
        TimeRecord startRecord = new TimeRecord(rightNow, TimeRecordType.START_WORK);

        assertThatThrownBy(() -> {
            TimeSlot timeSlot = new TimeSlot(startRecord);
            timeSlot.addEndTime(new TimeRecord(rightNowMinusThreeHours, TimeRecordType.END_WORK));
        })
                .isInstanceOf(TimeSlotOutOfBoundException.class)
                .hasMessage("End time must be before start time.");
    }

    @Test
    void testItShouldThrowAnExceptionWhenTryingToCreateWithATimeRecordWithInvalidRecordType() {
        ZonedDateTime rightNow = ZonedDateTime.now();
        TimeRecord startRecord = new TimeRecord(rightNow, TimeRecordType.END_WORK);

        assertThatThrownBy(() -> new TimeSlot(startRecord))
                .isInstanceOf(TimeSlotOutOfBoundException.class)
                .hasMessage("Cannot create a new time slot with start record type END_WORK.");
    }

    @Test
    void testItShouldThrowAnExceptionWhenTryingToAddAnEndTimeWithInvalidRecordType() {
        ZonedDateTime rightNow = ZonedDateTime.now();
        ZonedDateTime rightNowPlusThreeHours = rightNow.plusHours(3);
        TimeRecord startRecord = new TimeRecord(rightNow, TimeRecordType.START_WORK);

        assertThatThrownBy(() -> {
            TimeSlot timeSlot = new TimeSlot(startRecord);
            timeSlot.addEndTime(new TimeRecord(rightNowPlusThreeHours, TimeRecordType.START_WORK));
            })
                .isInstanceOf(TimeSlotOutOfBoundException.class)
                .hasMessage("Cannot add end time with record type START_WORK.");
    }
}
