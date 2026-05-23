package co.ucp.software.jefersson.reservation_backend.exception;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Thrown when creating a reservation for a date and time that is already booked.
 */
public class ReservationSlotAlreadyTakenException extends RuntimeException {

    /**
     * Creates the exception for the conflicting slot.
     *
     * @param date reservation date
     * @param time reservation time
     */
    public ReservationSlotAlreadyTakenException(LocalDate date, LocalTime time) {
        super("A reservation already exists for date %s and time %s".formatted(date, time));
    }
}
