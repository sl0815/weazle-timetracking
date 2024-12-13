package org.weazle.timetracking.domain.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.weazle.timetracking.domain.entity.CalendarEntity;

import java.time.LocalDate;

@Repository
public interface CalendarRepository extends CrudRepository<CalendarEntity, Long> {
    CalendarEntity findByActualDate(@NonNull final LocalDate actualDate);
}
