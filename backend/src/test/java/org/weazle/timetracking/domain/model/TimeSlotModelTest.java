package org.weazle.timetracking.domain.model;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.weazle.timetracking.adapter.api.model.TimeRecord;
import org.weazle.timetracking.adapter.api.model.TimeRecordType;
import org.weazle.timetracking.domain.model.exceptions.TimeSlotOutOfBoundException;

import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@JsonTest
public class TimeSlotModelTest {

    @Test
    void testItShouldCreateATimeSlotContainingOnlyAValidStartTime() throws TimeSlotOutOfBoundException {
        ZonedDateTime rightNow = ZonedDateTime.now();
        TimeRecord startRecord = new TimeRecord(rightNow, TimeRecordType.START_WORK);
        TimeSlotModel timeSlotModel = new TimeSlotModel(startRecord);

        assertThat(timeSlotModel.getStartRecord()).isNotNull();
        assertThat(timeSlotModel.getEndRecord()).isNull();
        assertThat(timeSlotModel.getStartRecord().getRecordedTime()).isEqualTo(rightNow);
    }

    @Test
    void testItShouldCreateATimeSlotContainingAValidStartAndEndTime() throws TimeSlotOutOfBoundException {
        ZonedDateTime rightNow = ZonedDateTime.now();
        ZonedDateTime rightNowInThreeHours = rightNow.plusHours(3);

        TimeRecord startRecord = new TimeRecord(rightNow, TimeRecordType.START_WORK);
        TimeRecord endRecord = new TimeRecord(rightNowInThreeHours, TimeRecordType.END_WORK);

        TimeSlotModel timeSlotModel = new TimeSlotModel(startRecord, endRecord);

        assertThat(timeSlotModel.getStartRecord()).isNotNull();
        assertThat(timeSlotModel.getEndRecord()).isNotNull();

        assertThat(timeSlotModel.getStartRecord().getRecordedTime()).isEqualTo(rightNow);
        assertThat(timeSlotModel.getEndRecord().getRecordedTime()).isEqualTo(rightNowInThreeHours);
    }

    @Test
    void testItShouldThrowAnExceptionWhenTryingToCreateWithAndEndTimeWhichIsBeforeTheStartTime() {
        ZonedDateTime rightNow = ZonedDateTime.now();
        ZonedDateTime rightNowMinusThreeHours = rightNow.minusHours(3);

        TimeRecord startRecord = new TimeRecord(rightNow, TimeRecordType.START_WORK);
        TimeRecord endRecord = new TimeRecord(rightNowMinusThreeHours, TimeRecordType.END_WORK);

        assertThatThrownBy(() -> new TimeSlotModel(startRecord, endRecord))
                .isInstanceOf(TimeSlotOutOfBoundException.class)
                .hasMessage("End time must be before start time.");
    }

    @Test
    void testItShouldThrowAnExceptionWhenTryingToAddAndEndTimeWhichIsBeforeTheStartTime() {
        ZonedDateTime rightNow = ZonedDateTime.now();
        ZonedDateTime rightNowMinusThreeHours = rightNow.minusHours(3);
        TimeRecord startRecord = new TimeRecord(rightNow, TimeRecordType.START_WORK);

        assertThatThrownBy(() -> {
            TimeSlotModel timeSlotModel = new TimeSlotModel(startRecord);
            timeSlotModel.addEndTime(new TimeRecord(rightNowMinusThreeHours, TimeRecordType.END_WORK));
        })
                .isInstanceOf(TimeSlotOutOfBoundException.class)
                .hasMessage("End time must be before start time.");
    }

    @Test
    void testItShouldThrowAnExceptionWhenTryingToCreateWithATimeRecordWithInvalidRecordType() {
        ZonedDateTime rightNow = ZonedDateTime.now();
        TimeRecord startRecord = new TimeRecord(rightNow, TimeRecordType.END_WORK);

        assertThatThrownBy(() -> new TimeSlotModel(startRecord))
                .isInstanceOf(TimeSlotOutOfBoundException.class)
                .hasMessage("Cannot create a new time slot with start record type END_WORK.");
    }

    @Test
    void testItShouldThrowAnExceptionWhenTryingToAddAnEndTimeWithInvalidRecordType() {
        ZonedDateTime rightNow = ZonedDateTime.now();
        ZonedDateTime rightNowPlusThreeHours = rightNow.plusHours(3);
        TimeRecord startRecord = new TimeRecord(rightNow, TimeRecordType.START_WORK);

        assertThatThrownBy(() -> {
            TimeSlotModel timeSlotModel = new TimeSlotModel(startRecord);
            timeSlotModel.addEndTime(new TimeRecord(rightNowPlusThreeHours, TimeRecordType.START_WORK));
            })
                .isInstanceOf(TimeSlotOutOfBoundException.class)
                .hasMessage("Cannot add end time with record type START_WORK.");
    }

    @Test
    void testItShouldCalculateHoursWorkedCorrectly() throws TimeSlotOutOfBoundException {
        ZonedDateTime rightNow = ZonedDateTime.now();
        ZonedDateTime rightNowInThreeHoursAnd25Minutes = rightNow.plusHours(3).plusMinutes(25);

        TimeRecord startRecord = new TimeRecord(rightNow, TimeRecordType.START_WORK);
        TimeRecord endRecord = new TimeRecord(rightNowInThreeHoursAnd25Minutes, TimeRecordType.END_WORK);

        TimeSlotModel timeSlotModel = new TimeSlotModel(startRecord, endRecord);

        assertThat(timeSlotModel.getStartRecord()).isNotNull();
        assertThat(timeSlotModel.getStartRecord().getRecordedTime()).isEqualTo(rightNow);
        assertThat(timeSlotModel.getEndRecord()).isNotNull();
        assertThat(timeSlotModel.getEndRecord().getRecordedTime()).isEqualTo(rightNowInThreeHoursAnd25Minutes);

        assertThat(timeSlotModel.getHoursWorkedInMinutes()).isEqualTo(205);
    }
}
