package org.weazle.timetracking.adapter.api;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.weazle.timetracking.adapter.api.model.TimeRecord;

@RestController
@RequestMapping("/api")
public class TimeTrackingController {

    @PostMapping(value = "track-time", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TimeRecord> recordTime(@RequestBody final TimeRecord record) {
        return ResponseEntity.ok(record);
    }
}
