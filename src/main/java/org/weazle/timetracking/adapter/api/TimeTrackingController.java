package org.weazle.timetracking.adapter.api;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import org.weazle.timetracking.adapter.api.model.TimeRecord;
import org.weazle.timetracking.domain.model.Workday;
import org.weazle.timetracking.domain.service.TimeTrackingImpl;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class TimeTrackingController {

    @NonNull
    private final TimeTrackingImpl timeTrackingService;

    public TimeTrackingController(@NonNull final TimeTrackingImpl timeTrackingService) {
        this.timeTrackingService = timeTrackingService;
    }

    @PostMapping(value = "track-time/{workdayUUID}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Workday> recordTime(
            @RequestBody final TimeRecord record,
            @PathVariable(required = false) UUID workdayUUID) {

        Workday workday = timeTrackingService.recordTime(workdayUUID, record);

        return ResponseEntity.ok(workday);
    }
}
