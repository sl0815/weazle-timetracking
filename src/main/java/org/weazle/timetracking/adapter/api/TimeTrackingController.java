package org.weazle.timetracking.adapter.api;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import org.weazle.timetracking.adapter.api.model.TimeRecord;
import org.weazle.timetracking.domain.entity.Calendar;
import org.weazle.timetracking.domain.model.Workday;
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

    @GetMapping(value="/today", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Calendar> today() {
        Calendar calendar = calendarRepository.findByActualDate(LocalDate.now());
        return ResponseEntity.ok(calendar);
    }

    @PostMapping(value = "track-time/{workdayUUID}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Workday> recordTime(
            @RequestBody final TimeRecord record,
            @PathVariable(required = false) UUID workdayUUID) {

        Workday workday = timeTrackingService.recordTime(workdayUUID, record);

        return ResponseEntity.ok(workday);
    }
}
