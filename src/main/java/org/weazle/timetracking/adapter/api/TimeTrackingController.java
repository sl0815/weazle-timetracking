package org.weazle.timetracking.adapter.api;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import org.weazle.timetracking.adapter.api.model.TimeRecord;

@RestController
@RequestMapping("/api")
public class TimeTrackingController {

    @PostMapping(value = "track-time", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TimeRecord> recordTime(@RequestBody final TimeRecord record) {
        return ResponseEntity.ok(record);
    }
}
