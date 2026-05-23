package co.ucp.software.jefersson.reservation_backend.dto;

import co.ucp.software.jefersson.reservation_backend.entity.ReservationStatus;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * API representation of a reservation.
 *
 * @param id           reservation identifier
 * @param customerName name of the customer
 * @param date         reservation date
 * @param time         reservation time
 * @param status       current reservation status
 */
public record ReservationResponse(
        Long id,
        String customerName,
        LocalDate date,
        LocalTime time,
        ReservationStatus status
) {}
