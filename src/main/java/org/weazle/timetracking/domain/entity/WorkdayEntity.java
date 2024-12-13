package org.weazle.timetracking.domain.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "workday")
public class WorkdayEntity {

    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "calendar_id", nullable = false)
    private CalendarEntity calendarEntity;

    @OneToMany(mappedBy = "workday", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TimeSlotEntity> timeSlotEntities;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public CalendarEntity getCalendar() {
        return calendarEntity;
    }

    public void setCalendar(CalendarEntity calendarEntity) {
        this.calendarEntity = calendarEntity;
    }

    public List<TimeSlotEntity> getTimeSlots() {
        return timeSlotEntities;
    }

    public void setTimeSlots(List<TimeSlotEntity> timeSlotEntities) {
        this.timeSlotEntities = timeSlotEntities;
    }
}
