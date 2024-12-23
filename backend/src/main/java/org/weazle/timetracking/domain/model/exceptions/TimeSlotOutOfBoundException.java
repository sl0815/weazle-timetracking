package org.weazle.timetracking.domain.model.exceptions;

public class TimeSlotOutOfBoundException extends Exception {
    public TimeSlotOutOfBoundException(String errorMessage) {
        super(errorMessage);
    }
}
