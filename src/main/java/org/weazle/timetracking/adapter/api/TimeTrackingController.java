package org.weazle.timetracking.adapter.api;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import org.weazle.timetracking.adapter.api.model.TimeRecord;
import org.weazle.timetracking.domain.entity.CalendarEntity;
import org.weazle.timetracking.domain.model.WorkdayModel;
import org.weazle.timetracking.domain.repository.CalendarRepository;
import org.weazle.timetracking.domain.service.TimeTrackingImpl;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class TimeTrackingController {

    @NonNull
    private final TimeTrackingImpl timeTrackingService;
    
    @NonNull
    private final CalendarRepository calendarRepository;

    public TimeTrackingController(
            @NonNull final TimeTrackingImpl timeTrackingService,
            @NonNull final CalendarRepository calendarRepository
            ) {
        this.timeTrackingService = timeTrackingService;
        this.calendarRepository = calendarRepository;
    }

    @GetMapping(
            value="/today",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CalendarEntity> today() {
        CalendarEntity calendarEntity = calendarRepository.findByActualDate(LocalDate.now());
        return ResponseEntity.ok(calendarEntity);
    }

    @PostMapping(
            value = "/track-time",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<WorkdayModel> recordTimeForNewWorkday(@RequestBody final TimeRecord record) {
        WorkdayModel workdayModel = timeTrackingService.recordTime(record);

        return ResponseEntity.ok(workdayModel);
    }

    @PostMapping(
            value = "/track-time-for-workday/{workdayUUID}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<WorkdayModel> recordTime(
            @RequestBody final TimeRecord record,
            @PathVariable(required = false) UUID workdayUUID) {

        WorkdayModel workdayModel = timeTrackingService.recordTimeForWorkday(workdayUUID, record);

        return ResponseEntity.ok(workdayModel);
    }
}
