package org.weazle.timetracking.adapter.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TimeRecord {

    private final ZonedDateTime recordedTime;
    private final TimeRecordType recordType;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public TimeRecord(
            @JsonProperty("recordedTime") ZonedDateTime trackedTime,
            @JsonProperty("recordType") TimeRecordType recordType) {
        this.recordedTime = trackedTime;
        this.recordType = recordType;
    }

    public ZonedDateTime getRecordedTime() {
        return recordedTime;
    }

    public TimeRecordType getRecordType() {
        return recordType;
    }
}
