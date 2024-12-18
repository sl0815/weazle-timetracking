package org.weazle.timetracking.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.springframework.lang.NonNull;

import java.time.LocalDate;

@Entity
@Table(name = "calendar")
public class CalendarEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_actual")
    private LocalDate actualDate;

    @Column(name = "month_name")
    private String nameOfMonth;

    @Column(name = "day_of_week")
    private String weekDay;

    @Column(name = "is_weekday")
    private boolean isWeekday;

    public CalendarEntity(@NonNull final LocalDate actualDate) {
        this.actualDate = actualDate;
    }

    public CalendarEntity() {
    }

    @JsonIgnore
    public Long getId() {
        return id;
    }

    public LocalDate getActualDate() {
        return actualDate;
    }

    public String getNameOfMonth() {
        return nameOfMonth;
    }

    public String getWeekDay() {
        return weekDay;
    }

    @JsonProperty("isWeekday")
    public boolean isWeekday() {
        return isWeekday;
    }
}
