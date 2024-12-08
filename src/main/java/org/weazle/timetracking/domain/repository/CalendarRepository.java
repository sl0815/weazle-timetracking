package org.weazle.timetracking.domain.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.weazle.timetracking.domain.entity.Calendar;

import java.time.LocalDate;

@Repository
public interface CalendarRepository extends CrudRepository<Calendar, Long> {
    Calendar findByActualDate(@NonNull final LocalDate actualDate);
}
