package org.weazle.timetracking.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.lang.NonNull;

import java.time.LocalDate;

@Entity
@Table(name = "calendar")
public class Calendar {

    @Id
    @Column(name = "date_actual")
    private LocalDate actualDate;

    @Column(name = "month_name")
    private String nameOfMonth;

    @Column(name = "day_of_week")
    private String weekDay;

    @Column(name = "is_weekday")
    private boolean isWeekday;

    // Hibernate expects entities to have a no-arg constructor,
    // though it does not necessarily have to be public.
    protected Calendar() {}

    public Calendar(@NonNull final LocalDate actualDate) {
        this.actualDate = actualDate;
    }

    public LocalDate getActualDate() {
        return actualDate;
    }

    public void setActualDate(LocalDate actualDate) {
        this.actualDate = actualDate;
    }

    public String getNameOfMonth() {
        return nameOfMonth;
    }

    public void setNameOfMonth(String nameOfMonth) {
        this.nameOfMonth = nameOfMonth;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    public boolean isWeekday() {
        return isWeekday;
    }

    public void setWeekday(boolean weekday) {
        isWeekday = weekday;
    }
}
