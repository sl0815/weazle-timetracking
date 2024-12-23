package org.weazle.timetracking.adapter.api.model;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class TimeRecordTest {

    @Autowired
    private JacksonTester<TimeRecord> jacksonTester;

    @Test
    void testItShouldDeserializeCorrectly() throws IOException {
        ZonedDateTime rightNow = ZonedDateTime.now();

        String jsonValue = "{\"recordedTime\":\"" + rightNow +"\",\"recordType\":\"START_WORK\"}";
        TimeRecord expectedRecord = jacksonTester.parseObject(jsonValue);

        assertThat(expectedRecord.getRecordedTime()).isEqualTo(rightNow);
        assertThat(expectedRecord.getRecordType()).isEqualTo(TimeRecordType.START_WORK);
    }
}
